package data.criteriabuilder;

import data.PredicateSpecification;
import data.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BookCriteriaBuilder {

    private List<PredicateSpecification> predicatesSpecification;

    private EntityManager entityManager;

    private BookCriteriaBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
        predicatesSpecification = new ArrayList<>();
    }

    public static BookCriteriaBuilder build(EntityManager entityManager) {
        return new BookCriteriaBuilder(entityManager);
    }

    public BookCriteriaBuilder addTitleCriteria(String title) {
        Optional.ofNullable(title).ifPresent((value) -> {
            predicatesSpecification.add(new PredicateSpecification<Book>() {
                @Override
                public Predicate toPredicate(Root<Book> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("title"), value);
                }
            });
        });
        return this;
    }

    public BookCriteriaBuilder addAuthorCriteria(String author) {
        Optional.ofNullable(author).ifPresent((value) -> {
            predicatesSpecification.add(new PredicateSpecification<Book>() {
                @Override
                public Predicate toPredicate(Root<Book> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("author"), value);
                }
            });
        });
        return this;
    }

    public BookCriteriaBuilder addSummaryCriteria(String summary) {
        Optional.ofNullable(summary).ifPresent((value) -> {
            predicatesSpecification.add(new PredicateSpecification<Book>() {
                @Override
                public Predicate toPredicate(Root<Book> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.like(root.get("summary"), "%"+value+"%");
                }
            });
        });
        return this;
    }


    public BookCriteriaBuilder addMaximumPriceCriteria(Double price) {
        Optional.ofNullable(price).ifPresent((value) -> {
            predicatesSpecification.add(new PredicateSpecification<Book>() {
                @Override
                public Predicate toPredicate(Root<Book> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
                }
            });
        });
        return this;
    }

    public BookCriteriaBuilder addDatePublicationCriteria(Date startDate, Date endDate) {
        if (startDate != null) {
            predicatesSpecification.add(new PredicateSpecification<Book>() {
                @Override
                public Predicate toPredicate(Root<Book> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    if (endDate != null) {
                        return criteriaBuilder.between(root.get("dateOfPublication"), startDate, endDate);
                    }
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfPublication"), startDate);
                }
            });
        } else if (endDate != null) {
            predicatesSpecification.add(new PredicateSpecification<Book>() {
                @Override
                public Predicate toPredicate(Root<Book> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("dateOfPublication"), endDate);
                }
            });
        }
        return this;
    }

    public BookCriteriaBuilder sortBy(Boolean asc, String sort) {
        Optional.ofNullable(sort).ifPresent((t) -> {
            predicatesSpecification.add(new PredicateSpecification<Book>() {
                @Override
                public Predicate toPredicate(Root<Book> root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sort)));
                    return criteriaBuilder.conjunction();
                }
            });
        });
        return this;
    }

    public CriteriaQuery getQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicatesSpecification.forEach((specification) -> {
            predicates.add(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        });
        Predicate[] predicateArray = new Predicate[predicates.size()];
        predicateArray = predicates.toArray(predicateArray);
        criteriaQuery.where(predicateArray);
        return criteriaQuery;
    }
}
