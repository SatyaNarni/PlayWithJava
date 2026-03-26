public class GenericSpecificationBuilder {

    public static <T> Specification<T> build(
            FetchGridDataRequest.SearchRequest request,
            Class<T> entityClass
    ) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // ✅ 1. FILTERS (attribute + values → IN)
            if (request.getFilters() != null) {
                for (FetchGridDataRequest.Filter filter : request.getFilters()) {

                    String field = filter.getAttribute();
                    List<String> values = filter.getValue();

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
@Component
public class GenericSearchExecutor {

    public <T> Page<T> execute(
            FetchGridDataRequest request,
            JpaSpecificationExecutor<T> repository,
            Class<T> entityClass
    ) {

        FetchGridDataRequest.SearchRequest searchRequest = request.getSearchRequest();

        // ✅ Build Specification
        Specification<T> spec =
                GenericSpecificationBuilder.build(searchRequest, entityClass);

        // ✅ Sorting
        List<Sort.Order> orders = new ArrayList<>();

        if (searchRequest.getSort() != null) {
            for (FetchGridDataRequest.Sort s : searchRequest.getSort()) {

                orders.add(new Sort.Order(
                        Sort.Direction.fromString(s.getOrder()), // ASC / DESC
                        s.getAttribute()
                ));
            }
        }

        // ✅ Pagination
        Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                orders.isEmpty() ? Sort.unsorted() : Sort.by(orders)
        );

        // ✅ Execute
        return repository.findAll(spec, pageable);
    }
}
