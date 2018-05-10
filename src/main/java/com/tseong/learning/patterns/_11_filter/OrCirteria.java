package com.tseong.learning.patterns._11_filter;

import java.util.List;

public class OrCirteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCirteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> firstCriteriaItems = criteria.meetCriteria(persons);
        List<Person> otherCirteriaItems = otherCriteria.meetCriteria(persons);

        for (Person person : otherCirteriaItems) {
            if (!firstCriteriaItems.contains(person)) {
                firstCriteriaItems.add(person);
            }
        }

        return firstCriteriaItems;
    }
}
