public class GenericSpecification {

    public static <T> Specification<T> build(SearchRequest request, Class<T> entityClass) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // ✅ 1. FILTERS (IN)
            if (request.getFilters() != null) {
                for (Map.Entry<String, List<Object>> entry : request.getFilters().entrySet()) {

                    String field = entry.getKey();
                    List<Object> values = entry.getValue();

                    if (values != null && !values.isEmpty()) {
                        predicates.add(root.get(field).in(values));
                    }
                }
            }

            // ✅ 2. GLOBAL SEARCH (LIKE on all String fields)
            if (request.getSearchText() != null && !request.getSearchText().isEmpty()) {

                String search = "%" + request.getSearchText().toLowerCase() + "%";

                List<Predicate> searchPredicates = new ArrayList<>();

                for (Field field : entityClass.getDeclaredFields()) {

                    if (field.getType() == String.class) {

                        searchPredicates.add(
                                cb.like(
                                        cb.lower(root.get(field.getName())),
                                        search
                                )
                        );
                    }
                }

                predicates.add(cb.or(searchPredicates.toArray(new Predicate[0])));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

--------
public <T> Page<T> search(
        SearchRequest request,
        JpaSpecificationExecutor<T> repository,
        Class<T> entityClass
) {

    // 🔹 Build Specification
    Specification<T> spec = GenericSpecification.build(request, entityClass);

    // 🔹 Sorting
    List<Sort.Order> orders = new ArrayList<>();

    if (request.getSort() != null) {
        for (SortRequest s : request.getSort()) {
            orders.add(new Sort.Order(
                    Sort.Direction.fromString(s.getDirection()),
                    s.getField()
            ));
        }
    }

    Pageable pageable = PageRequest.of(
            request.getPage(),
            request.getSize(),
            Sort.by(orders)
    );

    // 🔹 Execute
    return repository.findAll(spec, pageable);
}

-------

Page<Student> result = genericService.search(
        request,
        studentRepository,
        Student.class
);
