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
