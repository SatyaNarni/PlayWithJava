WITH cra_fields AS (
    SELECT
        q.section_id,
        q.question_id,
        q.question_number,
        q.question_text,
        q.display_order AS question_display_order,

        f.field_id,
        f.field_type,
        f.field_title,
        f.display_order AS field_display_order

    FROM sch_cra.form_ref_field f

    JOIN sch_cra.form_ref_question q
        ON q.question_id = f.question_id

    JOIN sch_cra.form_ref_field_version fv
        ON fv.field_id = f.field_id

    JOIN sch_cra.form_ref_question_version qv
        ON qv.question_id = q.question_id
       AND qv.version_id = fv.version_id

    JOIN sch_cra.form_ref_version v
        ON v.version_id = fv.version_id

    WHERE v.version_id = 'CRA-V5'
      AND q.section_id IN (
          'DND3',
          'GPR3',
          'PRA3',
          'GTR3',
          'CTP3'
      )
      AND f.field_title IN (
          'Source title',
          'Source URL'
      )
      AND f.parent_field_id IS NULL
)

SELECT
    cf.section_id,
    cf.question_id,
    cf.question_number,
    cf.question_text,
    cf.question_display_order,

    cf.field_id,
    cf.field_type,
    cf.field_title,
    cf.field_display_order,

    drg.*

FROM cra_fields cf

LEFT JOIN sch_cra.display_rule_group drg
    ON drg.target_field_id = cf.field_id

ORDER BY
    CASE cf.section_id
        WHEN 'DND3' THEN 1
        WHEN 'GPR3' THEN 2
        WHEN 'PRA3' THEN 3
        WHEN 'GTR3' THEN 4
        WHEN 'CTP3' THEN 5
        ELSE 999
    END,
    cf.question_display_order,
    cf.field_display_order;
