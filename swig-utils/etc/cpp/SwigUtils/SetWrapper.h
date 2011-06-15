#pragma once
#include <set>

template<class T>
class SetWrapper
{
private:
	std::set<T>* _set;
public:
	SetWrapper(std::set<T>* original)
	{
		this->_set = original;
	}

	~SetWrapper()
	{
	}

	int size()
	{
		return this->_set->size();
	}

	bool contains(T item)
	{
		std::set<T>::const_iterator iter = this->_set->find(item);
		return iter != this->_set->end();
	}

	bool add(T item)
	{
		return this->_set->insert(item).second;
	}

	void clear()
	{
		this->_set->clear();
	}

	bool remove(T item)
	{
		std::set<T>::const_iterator iter = this->_set->find(item);
		if (iter != this->_set->end()) {
			this->_set->erase(iter);
			return true;
		} else {
			return false;
		}
	}
};

template<class T>
class SetIterator
{
private:
	std::set<T>* _set;
	typename std::set<T>::iterator _iter;
public:
	SetIterator(std::set<T>* original)
	{
		this->_set = original;
		this->_iter = this->_set->begin();
	}

	bool hasNext()
	{
		return this->_iter != this->_set->end();
	}

	T next()
	{
		T ret = (T) *this->_iter;
		this->_iter++;
		return ret;
	}
};