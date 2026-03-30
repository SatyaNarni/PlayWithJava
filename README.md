import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class GenericSearchSpecificationBuilder {

    // ✅ Entity + Field based JOIN configuration
    private static final Map<String, String> JOIN_FIELDS = new HashMap<>();

    static {
        // format → EntityName.fieldName → column in joined table
        JOIN_FIELDS.put("Student.applicability", "val");
        JOIN_FIELDS.put("Order.status", "name");

        // add more if needed
    }

    public static <T> Specification<T> build(SearchRequest request, Class<T> entityClass) {

        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request != null && request.getFilters() != null && !request.getFilters().isEmpty()) {

                for (SearchRequest.Filter filter : request.getFilters()) {

                    String field = filter.getAttribute();
                    List<String> values = filter.getValue();

                    // ✅ skip invalid field
                    if (!hasField(entityClass, field)) {
                        continue;
                    }

                    if (values != null && !values.isEmpty()) {

                        try {
                            Field entityField = entityClass.getDeclaredField(field);
                            entityField.setAccessible(true);

                            Class<?> fieldType = entityField.getType();

                            String entityFieldKey = entityClass.getSimpleName() + "." + field;

                            List<Object> typedValues = new ArrayList<>();

                            for (String value : values) {

                                Object typedValue;

                                // ✅ Skip conversion for JOIN fields
                                if (JOIN_FIELDS.containsKey(entityFieldKey)) {
                                    typedValue = value;
                                } else {
                                    typedValue = convertStringToType(value, fieldType);
                                }

                                if (typedValue != null) {
                                    typedValues.add(typedValue);
                                }
                            }

                            if (!typedValues.isEmpty()) {

                                // ✅ JOIN handling
                                if (JOIN_FIELDS.containsKey(entityFieldKey)) {

                                    Join<Object, Object> join = root.join(field);
                                    String joinColumn = JOIN_FIELDS.get(entityFieldKey);

                                    if (typedValues.size() == 1) {
                                        predicates.add(cb.equal(join.get(joinColumn), typedValues.get(0)));
                                    } else {
                                        predicates.add(join.get(joinColumn).in(typedValues));
                                    }

                                } else {
                                    // ✅ Normal fields

                                    if (typedValues.size() == 1) {
                                        predicates.add(cb.equal(root.get(field), typedValues.get(0)));
                                    } else {
                                        predicates.add(root.get(field).in(typedValues));
                                    }
                                }
                            }

                        } catch (Exception e) {
                            // ignore invalid fields safely
                        }
                    }
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // ✅ Check if field exists in entity
    private static boolean hasField(Class<?> clazz, String fieldName) {
        return Arrays.stream(clazz.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }

    // ✅ Basic type conversion
    private static Object convertStringToType(String value, Class<?> type) {

        if (value == null) return null;

        try {
            if (type.equals(String.class)) {
                return value;
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.parseLong(value);
            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                return Integer.parseInt(value);
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return Boolean.parseBoolean(value);
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                return Double.parseDouble(value);
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
