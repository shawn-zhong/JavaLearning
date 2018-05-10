package com.tseong.learning.patterns._11_filter;

import java.util.List;

public interface Criteria {

    List<Person> meetCriteria(List<Person> persons);
}
