#pragma once
//#define BRIDGE_DEBUG
#include <BWAPI.h>
#include <BWTA.h>
#include <string>
#include <jni.h>
#include <windows.h>
#include "SwigBridge.h"

typedef jint (JNICALL CreateJavaVM_t)(JavaVM **pvm, void **penv, void *args);

class BridgeAIModule : public BWAPI::AIModule
{
	static HINSTANCE _libInst;
	static JavaVM* _jvm;
	static JNIEnv* _env;
	static jclass _delegate;
	jobject _bot;
	jclass _botClass;
	jmethodID _onFrame;
	jmethodID _onSendText;
	jmethodID _onPlayerLeft;
	jmethodID _onNukeDetect;
	jmethodID _onUnitCreate;
	jmethodID _onUnitDestroy;
	jmethodID _onUnitMorph;
	jmethodID _onUnitShow;
	jmethodID _onUnitHide;
	jmethodID _onUnitRenegade;
public:
	static void init(std::wstring dllDirectory);
	static void destroy();
	BridgeAIModule();
	~BridgeAIModule();
	void onStart();
	void onEnd(bool isWinner);
	void onFrame();
    bool onSendText(std::string text);
    void onPlayerLeft(BWAPI::Player* player);
    void onNukeDetect(BWAPI::Position target);
	void onUnitCreate(BWAPI::Unit* unit);
    void onUnitDestroy(BWAPI::Unit* unit);
    void onUnitMorph(BWAPI::Unit* unit);
    void onUnitShow(BWAPI::Unit* unit);
    void onUnitHide(BWAPI::Unit* unit);
	void onUnitRenegade(BWAPI::Unit* unit);
};
