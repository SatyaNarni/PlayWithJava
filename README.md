SELECT
    cc.questionnaire_version,

    q.question_id,
    qv.version,

    f.field_id,
    fv.version,
    f.is_mandatory,

    rg.rule_group_id,
    rgv.version,
    rg.rule_type,
    rg.rule_level,

    r.rule_id,

    c.condition_id,
    c.parent_field_id,
    c.parent_field_value,

    ad.field_value

FROM sch_cra.cra_case cc

JOIN sch_cra.form_ref_question_version qv
    ON qv.version = cc.questionnaire_version

JOIN sch_cra.form_ref_question q
    ON q.question_id = qv.question_id

JOIN sch_cra.form_ref_field_version fv
    ON fv.version = cc.questionnaire_version

JOIN sch_cra.form_ref_field f
    ON f.field_id = fv.field_id
   AND f.question_id = q.question_id

LEFT JOIN sch_cra.form_ref_display_rule_group_version rgv
    ON rgv.version = cc.questionnaire_version

LEFT JOIN sch_cra.form_ref_display_rule_group rg
    ON rg.rule_group_id = rgv.rule_group_id

LEFT JOIN sch_cra.form_ref_display_rule r
    ON r.rule_group_id = rg.rule_group_id

LEFT JOIN sch_cra.form_ref_condition c
    ON c.rule_id = r.rule_id

LEFT JOIN sch_cra.assessment_details ad
    ON ad.case_id = cc.case_id
   AND ad.field_id = f.field_id

WHERE cc.case_id = :caseId;
