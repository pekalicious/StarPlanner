#include "TestCpp.h"

TestCpp::TestCpp(void)
{
}

TestCpp::~TestCpp(void)
{
}

std::list<std::string> TestCpp::getStringList()
{
	std::list<std::string> list;
	list.push_back("1");
	list.push_back("2");
	list.push_back("3");
	list.push_back("4");
	list.push_back("5");
	return list;
}

std::string TestCpp::testListWrapper()
{
	try {
		std::list<std::string> list = this->getStringList();
		ListWrapper<std::string>* wrapper = new ListWrapper<std::string>(&list);
		//test contains
		if (!wrapper->contains("5"))
			return "Invalid contains!";
		//test add
		wrapper->add("6");
		//test size
		if (wrapper->size() != 6)
			return "Wrong size!";
		//test remove
		if (!wrapper->remove("6"))
			return "Couldn't remove!";
		//test iterator
		ListIterator<std::string>* iterator = new ListIterator<std::string>(&list);
		int count = 0;
		while (iterator->hasNext()) {
			count++;
			if (iterator->next().length() != 1)
				return "Invalid length!";
		}
		if (count != 5)
			return "Invalid count!";
		//test clear
		wrapper->clear();
		if (wrapper->size() != 0)
			return "Couldn't clear!";
	} catch (std::string ex) {
		return ex;
	}
	return "";
}

std::map<std::string, std::string> TestCpp::getStringMap()
{
	std::map<std::string, std::string> map;
	map["1"] = "1";
	map["2"] = "2";
	map["3"] = "3";
	map["4"] = "4";
	map["5"] = "5";
	return map;
}

std::string TestCpp::testMapWrapper()
{
	try {
		std::map<std::string, std::string> map = this->getStringMap();
		MapWrapper<std::string, std::string>* wrapper = new MapWrapper<std::string, std::string>(&map);
		//test add
		wrapper->add("6", "6");
		//test size
		if (wrapper->size() != 6)
			return "Wrong size!";
		//test remove
		if (!wrapper->remove("6") || wrapper->size() != 5)
			return "Couldn't remove!";
		//test iterator
		MapIterator<std::string, std::string>* iterator = new MapIterator<std::string, std::string>(&map);
		int count = 0;
		while (iterator->hasNext()) {
			count++;
			iterator->next();
			if (iterator->getKey().length() != 1 || iterator->getValue().length() != 1)
				return "Invalid length!";
		}
		if (count != 5)
			return "Invalid count!";
		//test clear
		wrapper->clear();
		if (wrapper->size() != 0)
			return "Couldn't clear!";
	} catch (std::string ex) {
		return ex;
	}
	return "";
}

std::set<std::string> TestCpp::getStringSet()
{
	std::set<std::string> set;
	set.insert("1");
	set.insert("2");
	set.insert("3");
	set.insert("4");
	set.insert("5");
	return set;
}

std::string TestCpp::testSetWrapper()
{
	try {
		std::set<std::string> set = this->getStringSet();
		SetWrapper<std::string>* wrapper = new SetWrapper<std::string>(&set);
		//test contains
		if (!wrapper->contains("5"))
			return "Invalid contains!";
		//test add
		wrapper->add("6");
		//test size
		if (wrapper->size() != 6)
			return "Wrong size!";
		//test remove
		if (!wrapper->remove("6"))
			return "Couldn't remove!";
		//test iterator
		SetIterator<std::string>* iterator = new SetIterator<std::string>(&set);
		int count = 0;
		while (iterator->hasNext()) {
			count++;
			if (iterator->next().length() != 1)
				return "Invalid length!";
		}
		if (count != 5)
			return "Invalid count!";
		//test clear
		wrapper->clear();
		if (wrapper->size() != 0)
			return "Couldn't clear!";
	} catch (std::string ex) {
		return ex;
	}
	return "";
}

std::vector<std::string> TestCpp::getStringVector()
{

	std::vector<std::string> vector;
	vector.push_back("1");
	vector.push_back("2");
	vector.push_back("3");
	vector.push_back("4");
	vector.push_back("5");
	return vector;
}

std::string TestCpp::testVectorWrapper()
{
	try {
		std::vector<std::string> vector = this->getStringVector();
		VectorWrapper<std::string>* wrapper = new VectorWrapper<std::string>(&vector);
		//test add
		wrapper->add(5, "6");
		//test set
		wrapper->set(3, "9");
		//test get
		if (wrapper->get(5) != "6")
			return "Couldn't get!";
		//test size
		if (wrapper->size() != 6)
			return "Wrong size!";
		//test remove
		if (wrapper->remove(5) != "6" || wrapper->size() != 5)
			return "Couldn't remove!";
		//test clear
		wrapper->clear();
		if (wrapper->size() != 0)
			return "Couldn't clear!";
	} catch (std::string ex) {
		return ex;
	}
	return "";
}