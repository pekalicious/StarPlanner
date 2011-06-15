#pragma once
#include <vector>

template<class T>
class VectorWrapper
{
private:
	std::vector<T>* _vector;
public:
	VectorWrapper(std::vector<T>* original)
	{
		this->_vector = original;
	}

	~VectorWrapper()
	{
	}

	int size()
	{
		return this->_vector->size();
	}

	void add(int index, T item)
	{
		this->_vector->insert(this->_vector->begin() + index, item);
	}

	void clear()
	{
		this->_vector->clear();
	}

	T set(int index, T item)
	{
		T ret = this->get(index);
		(*this->_vector)[index] = item;
		return ret;
	}

	T remove(int index)
	{
		T item = this->_vector->at(index);
		this->_vector->erase(this->_vector->begin() + index);
		return item;
	}

	T get(int index)
	{
		return this->_vector->at(index);
	}
};