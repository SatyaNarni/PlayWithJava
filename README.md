
Uploaded image
Give best mail content 

Here’s a cleaner, more professional version of your email—clear, structured, and to the point:

Subject: Confirmation Required: Source Fields & Label Casing for New Questions (V5)

Hi [Name],

I’m currently blocked while configuring field rules for the new questions as part of V5.

Since there are multiple input answer fields, could you please confirm the following:

Which answer fields require Source Title, Source URL, and Comments

Under what conditions (based on selected options) these fields should be mandatory

Additionally, once confirmed:

The ADO needs to be updated accordingly

The Figma designs also need to be aligned with these rules

I also need your confirmation on the field label casing used in the UI.
Currently, the labels are in lowercase, whereas existing fields follow Title Case.

Example:

roadmap investments | meaningfulness (lowercase)

Roadmap Investments | Meaningfulness (Title Case)

Please confirm the preferred standard so we can maintain consistency across the application.

Thanks,

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
