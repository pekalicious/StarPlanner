#define WIN32_LEAN_AND_MEAN
#include <windows.h>
#include <stdio.h>
#include <tchar.h>
#include <BWAPI.h>
#include <BWTA.h>
#include <jni.h>
#include "BridgeAIModule.h"

using namespace std;

wstring _dllDirectory;

namespace BWAPI { Game* Broodwar; }

BOOL APIENTRY DllMain(HANDLE hModule, DWORD  ul_reason_for_call, LPVOID lpReserved)
{
	switch (ul_reason_for_call)
	{
	case DLL_PROCESS_ATTACH:
		{
			//get the DLL directory
			wchar_t* filename = new wchar_t[300];
			GetModuleFileName((HMODULE) hModule, filename, 300);
			_dllDirectory = filename;
			_dllDirectory = _dllDirectory.substr(0, _dllDirectory.find_last_of('\\') + 1);
			//init BWAPI
			BWAPI::BWAPI_init();
		}
		break;
	case DLL_PROCESS_DETACH:
		//destroy bridge
		BridgeAIModule::destroy();
		break;
	}
	return TRUE;
}

extern "C" __declspec(dllexport) BWAPI::AIModule* newAIModule(BWAPI::Game* game)
{
	BWAPI::Broodwar = game;
	//init
	BridgeAIModule::init(_dllDirectory);
	return new BridgeAIModule();
}