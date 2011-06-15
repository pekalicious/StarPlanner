package org.cretz.swig.ant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * Ant task to add "RegisterNative" code to SWIG gen'd files
 * 
 * @author Chad Retz
 */
public class RegisterNativesTask extends Task {
    
    /**
     * Get a type signature from a class
     * 
     * @param cls
     * @return
     */
    private static String getJNITypeSignature(Class<?> cls) {
        String ret = "";
        if (cls.isArray()) {
            ret += '[';
        }
        if ("boolean".equals(cls.getName())) {
            ret += 'Z';
        } else if ("byte".equals(cls.getName())) {
            ret += 'B';
        } else if ("char".equals(cls.getName())) {
            ret += 'C';
        } else if ("short".equals(cls.getName())) {
            ret += 'S';
        } else if ("int".equals(cls.getName())) {
            ret += 'I';
        } else if ("long".equals(cls.getName())) {
            ret += 'J';
        } else if ("float".equals(cls.getName())) {
            ret += 'F';
        } else if ("double".equals(cls.getName())) {
            ret += 'D';
        } else if ("void".equals(cls.getName())) {
            ret += 'V';
        } else {
            ret += 'L' + cls.getName().replace('.', '/') + ';';
        }
        return ret;
    }
    
    /**
     * Get a JNI signature for the given method
     * 
     * @param method
     * @return
     */
    private static String getJNISignature(Method method) {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        for (Class<?> parameter : method.getParameterTypes()) {
            builder.append(getJNITypeSignature(parameter));
        }
        builder.append(')');
        builder.append(getJNITypeSignature(method.getReturnType()));
        return builder.toString();
    }
    
    /**
     * Load an entire fine into a string
     * 
     * @param file
     * @return
     * @throws IOException
     */
    private static String loadFileToString(File file) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(file));
        StringBuilder builder = new StringBuilder();
        try {
            String line = reader.readLine();
            while (line != null) {
                builder.append(line + "\r\n");
                line = reader.readLine();
            }
            return builder.toString();
        } finally {
            reader.close();
        }
    }
    
    /**
     * Write an entire string to a file
     * 
     * @param file
     * @param string
     * @throws IOException
     */
    private static void writeStringToFile(File file, String string) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            writer.write(string);
        } finally {
            writer.close();
        }
    }

    private String source;
    private String module;
    private String pkg;
    
    @Override
    public void execute() {
        if (source == null) {
            throw new BuildException("source is required");
        }
        if (module == null) {
            throw new BuildException("module is required");
        }
        if (pkg == null) {
            throw new BuildException("_package is required");
        }
        //get cpp and header file
        File cppFile = new File(source);
        File headerFile = new File(source.replace(".cpp", ".h"));
        if (!cppFile.exists()) {
            throw new BuildException("Can't find source file");
        }
        if (!headerFile.exists()) {
            throw new BuildException("Can't find header file");
        }
        String cppContents;
        String headerContents;
        try {
            cppContents = loadFileToString(cppFile);
            headerContents = loadFileToString(headerFile);
        } catch (IOException e) {
            throw new BuildException("Unable to load cpp or header file", e);
        }
        //find class
        Class<?> cls;
        try {
            cls = Class.forName(pkg + "." + module + "JNI");
        } catch (ClassNotFoundException e) {
            throw new BuildException("Can't find JNI class", e);
        }
        //load up the methods
        List<JNIMethod> methods = new ArrayList<JNIMethod>();
        String prefix = "Java_" + cls.getName().replace('.', '_');
        for (Method method : cls.getDeclaredMethods()) {
            if (Modifier.isNative(method.getModifiers())) {
                //get the part before the underscore
                String methodName = method.getName();
                String jniMethod;
                jniMethod = prefix + '_' + methodName.replace("_", "_1");
                if (!cppContents.contains("JNICALL " + jniMethod + "(")) {
                    log("Can't find JNI method, skipping: " + jniMethod, 
                            Project.MSG_WARN);
                } else {
                    methods.add(new JNIMethod(methodName, getJNISignature(method), 
                            jniMethod));
                }
            }
        }
        //write pieces to header and cpp
        headerContents = "#pragma once\r\n#include <jni.h>\r\n\r\n" +
                headerContents + "\r\n\r\nclass SwigUtils {\r\npublic:\r\n\t" +
                "static int registerNatives(JNIEnv* env);\r\n};";
        StringBuilder cppMethods = new StringBuilder();
        cppMethods.append("int SwigUtils::registerNatives(JNIEnv* env)\r\n{\r\n");
        cppMethods.append("\tJNINativeMethod methods[" + methods.size() + "];\r\n");
        cppMethods.append("\tjclass cls = env->FindClass(\"");
        cppMethods.append(cls.getName().replace('.', '/') + "\");\r\n");
        for (int i = 0; i < methods.size(); i++) {
            JNIMethod method = methods.get(i);
            cppMethods.append("\r\n\tmethods[" + i + "].name = \"" +
                    method.name + "\";\r\n");
            cppMethods.append("\tmethods[" + i + "].signature = \"" +
                    method.signature + "\";\r\n");
            cppMethods.append("\tmethods[" + i + "].fnPtr = (void*)&" +
                    method.cppSignature + ";\r\n");
        }
        cppMethods.append("\r\n\treturn (int) env->RegisterNatives(cls, methods, " +
                methods.size() + ");\r\n}");
        try {
            writeStringToFile(headerFile, headerContents);
            writeStringToFile(cppFile, cppContents + "\r\n\r\n" + 
                    cppMethods.toString());
        } catch (IOException e) {
            throw new BuildException("Unable to write cpp or header file", e);
        }
    }
    
    /**
     * The source cpp file to alter (also assumes the
     * header file is there)
     *  
     * @return
     */
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    /**
     * The name of the SWIG module
     * 
     * @return
     */
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    /**
     * The package the source is in
     * 
     * @return
     */
    public String getPackage() {
        return pkg;
    }

    public void setPackage(String pkg) {
        this.pkg = pkg;
    }

    /**
     * Simple POJO for holding method information
     * 
     * @author Chad Retz
     */
    private static final class JNIMethod {
        private final String name;
        private final String signature;
        private final String cppSignature;
        
        private JNIMethod(String name, String signature, String cppSignature) {
            this.name = name;
            this.signature = signature;
            this.cppSignature = cppSignature;
        }
    }
}
