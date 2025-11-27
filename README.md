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
