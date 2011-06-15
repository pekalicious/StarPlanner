#pragma once
#include <map>

template<class K, class V>
class MapWrapper
{
private:
	std::map<K, V>* _map;
public:
	MapWrapper(std::map<K, V>* original)
	{
		this->_map = original;
	}

	~MapWrapper()
	{
	}

	int size()
	{
		return this->_map->size();
	}

	bool add(K key, V value)
	{
		bool present = this->_map->find(key) != this->_map->end();
		(*this->_map)[key] = value;
		return present;
	}

	void clear()
	{
		this->_map->clear();
	}

	bool remove(K key)
	{
		std::map<K, V>::const_iterator iter = this->_map->find(key);
		if (iter != this->_map->end()) {
			this->_map->erase(iter);
			return true;
		} else {
			return false;
		}
	}
};

template<class K, class V>
class MapIterator
{
private:
	std::map<K, V>* _map;
	typename std::map<K, V>::iterator _iter;
	std::pair<K, V> _current;
public:
	MapIterator(std::map<K, V>* original)
	{
		this->_map = original;
		this->_iter = this->_map->begin();
	}

	bool hasNext()
	{
		return this->_iter != this->_map->end();
	}

	void next()
	{
		this->_current = *this->_iter;
		this->_iter++;
	}

	K getKey()
	{
		return this->_current.first;
	}

	V getValue()
	{
		return this->_current.second;
	}
};
