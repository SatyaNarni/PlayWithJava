SELECT
    f.field_id,
    f.field_name,
    q.question_id,
    q.question_text
FROM form_ref_field f
JOIN form_ref_question q
    ON q.question_id = f.question_id
JOIN form_ref_field_version fv
    ON fv.field_id = f.field_id
JOIN form_ref_question_version qv
    ON qv.question_id = q.question_id
   AND qv.form_version_id = fv.form_version_id
JOIN form_ref_version v
    ON v.form_version_id = fv.form_version_id
WHERE v.form_code = 'CRA-V5'
ORDER BY q.question_id, f.field_id;
