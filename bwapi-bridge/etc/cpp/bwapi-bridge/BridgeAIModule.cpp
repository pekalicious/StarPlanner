#include "BridgeAIModule.h"

std::string wstr_to_str(std::wstring str)
{
	size_t returned;
	char* ascii = new char[str.length() + 1];
	wcstombs_s(&returned, ascii, str.length() + 1, str.c_str(), _TRUNCATE);
	return ascii;
}

std::wstring str_to_wstr(std::string str)
{
	size_t returned;
	wchar_t* ret = new wchar_t[str.length() + 1];
	mbstowcs_s(&returned, ret, str.length() + 1, str.c_str(), _TRUNCATE);
	return ret;
}

bool file_exists(const char * filename)
{
    if (FILE * file = fopen(filename, "r"))
    {
        fclose(file);
        return true;
    }
    return false;
}

std::string get_jars(std::wstring dir)
{
	WIN32_FIND_DATA findFileData;
	HANDLE hFind;
	hFind = FindFirstFile((dir + str_to_wstr("*.jar")).c_str(), &findFileData);
	if (hFind == INVALID_HANDLE_VALUE) {
		return "";
	} else {
		std::string str_dir = wstr_to_str(dir);
		std::string ret = str_dir + wstr_to_str(findFileData.cFileName);
		while(FindNextFile(hFind, &findFileData)) {
			//since we're on windows, this is the separator
			ret += ';';
			ret += str_dir + wstr_to_str(findFileData.cFileName);
		}
		FindClose(hFind);
		return ret;
	}
}

HINSTANCE BridgeAIModule::_libInst;
JavaVM* BridgeAIModule::_jvm;
JNIEnv* BridgeAIModule::_env;
jclass BridgeAIModule::_delegate;

void BridgeAIModule::init(std::wstring dllDirectory)
{
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf("Initializing bridge...");
#endif
	BridgeAIModule::_libInst = NULL;
	BridgeAIModule::_jvm = NULL;
	BridgeAIModule::_delegate = NULL;
	//create vm
	//get dll location
	std::string env = getenv("JAVA_HOME");
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf((((std::string)"JAVA_HOME: ") + env).c_str());
#endif
	if (file_exists((env + "\\bin\\client\\jvm.dll").c_str())) {
		env += "\\bin\\client\\jvm.dll";
	} else if (file_exists((env + "\\jre\\bin\\client\\jvm.dll").c_str())) {
		//prolly a JDK
		env += "\\jre\\bin\\client\\jvm.dll";
	} else {
		BWAPI::Broodwar->printf("JVM Dll not found; Is JAVA_HOME set?");
		return;
	}
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf((((std::string)"DLL: ") + env).c_str());
#endif
	//load it
	if ( (BridgeAIModule::_libInst = LoadLibrary(str_to_wstr(env).c_str())) == NULL) {
		BWAPI::Broodwar->printf("Can't load JVM DLL");
		return;
	}
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf("DLL loaded");
#endif
	//grab vm creation method
	CreateJavaVM_t* createFn = (CreateJavaVM_t *)GetProcAddress(BridgeAIModule::_libInst, "JNI_CreateJavaVM");
    if (createFn == NULL) {
        BWAPI::Broodwar->printf("Can't locate JNI_CreateJavaVM");
		return;
    }
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf("Creation method found");
#endif
	//build options
	JavaVMInitArgs initArgs;
	JavaVMOption* options;
#ifdef BRIDGE_DEBUG
	options = new JavaVMOption[5];
#else
	options = new JavaVMOption[1];
#endif
	std::string classpath = "-Djava.class.path=";
	//we want all jars in our directory
	std::string jars = get_jars(dllDirectory);
	if (jars == "") {
		return;
	}
	classpath += jars;
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf((((std::string) "Classpath: ") + classpath).c_str());
#endif
	options[0].optionString = (char *) classpath.c_str();
#ifdef BRIDGE_DEBUG
	options[1].optionString = "-Xdebug";
	options[2].optionString = "-Xrunjdwp:transport=dt_socket,address=7979,server=y,suspend=n";
	options[3].optionString = "-Xnoagent";
	options[4].optionString = "-Djava.compiler=NONE";
#endif
	//assuming 1.6 here
	initArgs.version = JNI_VERSION_1_6;
#ifdef BRIDGE_DEBUG
	initArgs.nOptions = 5;
#else
	initArgs.nOptions = 1;
#endif
	initArgs.options = options;
	initArgs.ignoreUnrecognized = false;
	//create vm
	if (createFn(&BridgeAIModule::_jvm, (void **)&(BridgeAIModule::_env), &initArgs) != 0) {
		delete options;
		BWAPI::Broodwar->printf("Can't create VM");
		return;
	}
	delete options;
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf("VM created");
#endif
	//register them natives...
	int regRes = SwigUtils::registerNatives(BridgeAIModule::_env);
	if (regRes != 0) {
		BWAPI::Broodwar->printf("Couldn't register natives");
	}
	//grab delegate class
	BridgeAIModule::_delegate = BridgeAIModule::_env->FindClass("org/bwapi/bridge/model/BridgeDelegator");
	if (BridgeAIModule::_delegate == NULL) {
		BridgeAIModule::_jvm->DestroyJavaVM();
		BridgeAIModule::_jvm = NULL;
		BWAPI::Broodwar->printf("Can't find delegate class; Is bridge JAR present?");
		return;
	}
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf("Delegate found");
#endif
}

void BridgeAIModule::destroy()
{
	if (BridgeAIModule::_jvm != NULL) {
		BridgeAIModule::_jvm->DestroyJavaVM();
	}
	if (BridgeAIModule::_libInst != NULL) {
		FreeLibrary(BridgeAIModule::_libInst);
	}
}

BridgeAIModule::BridgeAIModule()
{
}

BridgeAIModule::~BridgeAIModule()
{
}

void BridgeAIModule::onStart() 
{
#ifdef BRIDGE_DEBUG
	BWAPI::Broodwar->printf("Game started");
#endif
	this->_botClass = this->_env->FindClass("org/bwapi/bridge/BridgeBot");
	jmethodID method = this->_env->GetStaticMethodID(BridgeAIModule::_delegate, 
		"load", "()Lorg/bwapi/bridge/BridgeBot;");
	this->_bot = BridgeAIModule::_env->CallStaticObjectMethod(BridgeAIModule::_delegate, method);
	//load up the methods
	this->_onFrame = BridgeAIModule::_env->GetMethodID(this->_botClass, "onFrame", "()V");
	this->_onSendText = BridgeAIModule::_env->GetMethodID(this->_botClass, "onSendText", "(Ljava/lang/String;)Z");
	this->_onNukeDetect = BridgeAIModule::_env->GetStaticMethodID(BridgeAIModule::_delegate,
		"onNukeDetect", "(Lorg/bwapi/bridge/BridgeBot;J)V");
	this->_onPlayerLeft = BridgeAIModule::_env->GetStaticMethodID(BridgeAIModule::_delegate,
		"onPlayerLeft", "(Lorg/bwapi/bridge/BridgeBot;J)V");
	this->_onUnitCreate = BridgeAIModule::_env->GetStaticMethodID(BridgeAIModule::_delegate,
		"onUnitCreate", "(Lorg/bwapi/bridge/BridgeBot;J)V");
	this->_onUnitDestroy = BridgeAIModule::_env->GetStaticMethodID(BridgeAIModule::_delegate,
		"onUnitDestroy", "(Lorg/bwapi/bridge/BridgeBot;J)V");
	this->_onUnitMorph = BridgeAIModule::_env->GetStaticMethodID(BridgeAIModule::_delegate,
		"onUnitMorph", "(Lorg/bwapi/bridge/BridgeBot;J)V");
	this->_onUnitShow = BridgeAIModule::_env->GetStaticMethodID(BridgeAIModule::_delegate,
		"onUnitShow", "(Lorg/bwapi/bridge/BridgeBot;J)V");
	this->_onUnitHide = BridgeAIModule::_env->GetStaticMethodID(BridgeAIModule::_delegate,
		"onUnitHide", "(Lorg/bwapi/bridge/BridgeBot;J)V");
	this->_onUnitRenegade = BridgeAIModule::_env->GetStaticMethodID(BridgeAIModule::_delegate,
		"onUnitRenegade", "(Lorg/bwapi/bridge/BridgeBot;J)V");
	//call the start
	BridgeAIModule::_env->CallVoidMethod(this->_bot, BridgeAIModule::_env->GetMethodID(this->_botClass, "onStart", "()V"));
}

void BridgeAIModule::onEnd(bool isWinner)
{
	BridgeAIModule::_env->CallVoidMethod(this->_bot, BridgeAIModule::_env->GetMethodID(
		this->_botClass, "onEnd", "(Z)V"), isWinner ? JNI_TRUE : JNI_FALSE);
}

void BridgeAIModule::onFrame()
{
	BridgeAIModule::_env->CallVoidMethod(this->_bot, this->_onFrame);
}

bool BridgeAIModule::onSendText(std::string text)
{
	return BridgeAIModule::_env->CallBooleanMethod(this->_bot, this->_onSendText, 
		BridgeAIModule::_env->NewStringUTF(text.c_str())) == JNI_TRUE;
}

void BridgeAIModule::onPlayerLeft(BWAPI::Player* player)
{
	BridgeAIModule::_env->CallStaticVoidMethod(BridgeAIModule::_delegate,
		this->_onPlayerLeft, this->_bot, (jlong) player);
}

void BridgeAIModule::onNukeDetect(BWAPI::Position position)
{
	BridgeAIModule::_env->CallStaticVoidMethod(BridgeAIModule::_delegate,
		this->_onNukeDetect, this->_bot, (jlong) &position);
}

void BridgeAIModule::onUnitCreate(BWAPI::Unit* unit)
{
	BridgeAIModule::_env->CallStaticVoidMethod(BridgeAIModule::_delegate,
		this->_onUnitCreate, this->_bot, (jlong) unit);
}

void BridgeAIModule::onUnitDestroy(BWAPI::Unit* unit)
{
	BridgeAIModule::_env->CallStaticVoidMethod(BridgeAIModule::_delegate,
		this->_onUnitDestroy, this->_bot, (jlong) unit);
}

void BridgeAIModule::onUnitMorph(BWAPI::Unit* unit)
{
	BridgeAIModule::_env->CallStaticVoidMethod(BridgeAIModule::_delegate,
		this->_onUnitMorph, this->_bot, (jlong) unit);
}

void BridgeAIModule::onUnitShow(BWAPI::Unit* unit)
{
	BridgeAIModule::_env->CallStaticVoidMethod(BridgeAIModule::_delegate,
		this->_onUnitShow, this->_bot, (jlong) unit);
}

void BridgeAIModule::onUnitHide(BWAPI::Unit* unit)
{
	BridgeAIModule::_env->CallStaticVoidMethod(BridgeAIModule::_delegate,
		this->_onUnitHide, this->_bot, (jlong) unit);
}

void BridgeAIModule::onUnitRenegade(BWAPI::Unit* unit)
{
	BridgeAIModule::_env->CallStaticVoidMethod(BridgeAIModule::_delegate,
		this->_onUnitRenegade, this->_bot, (jlong) unit);
}