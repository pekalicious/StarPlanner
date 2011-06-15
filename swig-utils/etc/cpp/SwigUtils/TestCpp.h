#pragma once
#include <stdio.h>
#include <string>
#include <list>
#include <map>
#include <set>
#include <vector>
#include "ListWrapper.h"
#include "MapWrapper.h"
#include "SetWrapper.h"
#include "VectorWrapper.h"

class TestCpp
{
public:
	TestCpp(void);
	~TestCpp(void);
	std::list<std::string> getStringList();
	std::string testListWrapper();
	std::map<std::string, std::string> getStringMap();
	std::string testMapWrapper();
	std::set<std::string> getStringSet();
	std::string testSetWrapper();
	std::vector<std::string> getStringVector();
	std::string testVectorWrapper();
};
