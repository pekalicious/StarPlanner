%include "stl.i"

%module(directors="1") swigutils
%{
#include <string>
#include "ListWrapper.h"
#include "MapWrapper.h"
#include "SetWrapper.h"
#include "VectorWrapper.h"
#include "TestCpp.h"
%}

//Operators
%rename (opAdd) *::operator+=;
%rename (opAssign) *::operator=;
%rename (opEquals) *::operator==;
%rename (opLessThan) *::operator<;
%rename (opMinus) *::operator-;
%rename (opNotEquals) *::operator!=;
%rename (opPlus) *::operator+;
%rename (opSubtract) *::operator-=;

%include "../etc/cpp/SwigUtils/ListWrapper.h"
%include "../etc/cpp/SwigUtils/MapWrapper.h"
%include "../etc/cpp/SwigUtils/SetWrapper.h"
%include "../etc/cpp/SwigUtils/VectorWrapper.h"
%include "../etc/cpp/SwigUtils/TestCpp.h"

%template (StringSetWrapper) SetWrapper<std::string>;
%template (StringSetIterator) SetIterator<std::string>;
%template (StringListWrapper) ListWrapper<std::string>;
%template (StringListIterator) ListIterator<std::string>;
%template (StringVectorWrapper) VectorWrapper<std::string>;
%template (StringMapWrapper) MapWrapper<std::string, std::string>;
%template (StringMapIterator) MapIterator<std::string, std::string>;