# PlayWithJava
Java #HashMap Sorting/ Various #Patterns Printing/ String #Split Without Split Method

DO $$
DECLARE
    start_date DATE := '2022-01-01';
    end_date   DATE := '2026-01-01';
    part_start DATE;
    part_end   DATE;
BEGIN
    part_start := start_date;
    WHILE part_start < end_date LOOP
        part_end := (part_start + INTERVAL '1 month')::date;
        EXECUTE format('
            CREATE TABLE case_details_new_%s
            PARTITION OF case_details_new
            FOR VALUES FROM (%L) TO (%L);
        ',
        to_char(part_start, 'YYYY_MM'),
        part_start, part_end);

        part_start := part_end;
    END LOOP;
END$$;
SELECT
  CASE
    WHEN GROUPING(date_trunc('day', created_on)) = 0 THEN 'DAILY'
    WHEN GROUPING(date_trunc('week', created_on)) = 0 THEN 'WEEKLY'
    WHEN GROUPING(date_trunc('month', created_on)) = 0 THEN 'MONTHLY'
    WHEN GROUPING(date_trunc('quarter', created_on)) = 0 THEN 'QUARTERLY'
    WHEN GROUPING(date_trunc('year', created_on)) = 0 THEN 'YEARLY'
  END AS period_type,

  date_trunc('day', created_on)     AS day,
  date_trunc('week', created_on)    AS week,
  date_trunc('month', created_on)   AS month,
  date_trunc('quarter', created_on) AS quarter,
  date_trunc('year', created_on)    AS year,

  COUNT(*) AS case_count
FROM case_table
GROUP BY GROUPING SETS (
  (date_trunc('day', created_on)),
  (date_trunc('week', created_on)),
  (date_trunc('month', created_on)),
  (date_trunc('quarter', created_on)),
  (date_trunc('year', created_on))
)
ORDER BY year, quarter, month, week, day;


<<<<<<>>>>>

SELECT
  CASE
    WHEN GROUPING(day) = 0 THEN 'DAILY'
    WHEN GROUPING(week) = 0 THEN 'WEEKLY'
    WHEN GROUPING(month) = 0 THEN 'MONTHLY'
    WHEN GROUPING(quarter) = 0 THEN 'QUARTERLY'
    WHEN GROUPING(year) = 0 THEN 'YEARLY'
  END AS period_type,

  day,
  week,
  month,
  quarter,
  year,

  COUNT(*) AS case_count
FROM (
  SELECT
    created_on AT TIME ZONE 'UTC' AS created_utc,  -- adjust timezone if needed

    DATE(created_on AT TIME ZONE 'UTC')                         AS day,
    DATE_TRUNC('week', created_on AT TIME ZONE 'UTC')::date     AS week,
    DATE_TRUNC('month', created_on AT TIME ZONE 'UTC')::date    AS month,
    DATE_TRUNC('quarter', created_on AT TIME ZONE 'UTC')::date  AS quarter,
    DATE_TRUNC('year', created_on AT TIME ZONE 'UTC')::date     AS year
  FROM case_table
) t
GROUP BY GROUPING SETS (
  (day),
  (week),
  (month),
  (quarter),
  (year)
)
ORDER BY year, quarter, month, week, day;
-------SELECT
    date_trunc('quarter', c.caseCreatedOn) AS quarter,
    COUNT(d.adid) AS assessment_record_count
FROM cra_case c
JOIN cra_case_assessment_detail d
    ON d.case_id = c.case_id
WHERE c.assessment_status = 'Completed'
GROUP BY date_trunc('quarter', c.caseCreatedOn)
ORDER BY quarter;
------
SELECT
    c.*,
    COUNT(ad.case_id) AS assessment_count
FROM case c
LEFT JOIN assessment_detail ad
    ON ad.case_id = c.case_id
GROUP BY c.case_id;
_______
INSERT INTO assessment_detail_new (
    case_id,
    score,
    remarks,
    created
)
SELECT
    ad.case_id,
    ad.score,
    ad.remarks,
    ad.created
FROM assessment_detail ad
JOIN "case" c
    ON c.case_id = ad.case_id
WHERE
    c.assessment_status = 'APPROVED'
    AND EXTRACT(YEAR FROM ad.created) = 2025
    AND EXTRACT(QUARTER FROM ad.created) = 2;

