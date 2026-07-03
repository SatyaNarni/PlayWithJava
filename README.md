SELECT
    q.question_id,
    q.depends_on AS question_depends_on,

    f.field_id,
    f.is_mandatory,
    f.depends_on AS field_depends_on,

    rg.rule_group_id,
    rg.rule_type,
    rg.rule_level,
    rg.target_question_id,
    rg.target_field_id,
    rg.rule_pattern,

    r.rule_id,

    c.condition_id,
    c.parent_field_id,
    c.parent_field_value,

    ad.case_id,
    ad.field_value

FROM sch_cra.form_ref_field f
JOIN sch_cra.form_ref_question q
    ON q.question_id = f.question_id

LEFT JOIN sch_cra.form_ref_display_rule_group rg
    ON (
        (rg.rule_level = 'QUESTION'
         AND rg.target_question_id = q.question_id)
        OR
        (rg.rule_level = 'FIELD'
         AND rg.target_field_id = f.field_id)
    )

LEFT JOIN sch_cra.form_ref_display_rule r
    ON r.rule_group_id = rg.rule_group_id

LEFT JOIN sch_cra.form_ref_condition c
    ON c.rule_id = r.rule_id

LEFT JOIN sch_cra.assessment_details ad
    ON ad.field_id = f.field_id
   AND ad.case_id = :caseId

WHERE ad.case_id = :caseId
ORDER BY
    q.question_id,
    f.field_id,
    rg.rule_group_id,
    r.rule_id,
    c.condition_id;
