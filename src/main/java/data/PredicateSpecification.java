package data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface PredicateSpecification<T> {

    Predicate toPredicate(Root<T> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder);
}
