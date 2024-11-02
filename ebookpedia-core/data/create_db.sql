CREATE DATABASE ebookpedia;
CREATE USER app WITH ENCRYPTED PASSWORD 'T0pS3cr3t!';



CREATE TABLE user_account (
	id VARCHAR(255) not null,
	username VARCHAR(255) not null,
    password VARCHAR(255) not null,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
    

CREATE TABLE user_profile (
    id integer not null,
    name varchar(255) not null,
    PRIMARY KEY (id)
);

select * from user_profile

CREATE TABLE user_account_user_profile (
  user_account_id VARCHAR(255) NOT NULL,
  user_profile_id INTEGER NOT NULL,
  CONSTRAINT user_account_user_profile_fk1 FOREIGN KEY (user_account_id) REFERENCES user_account(id),
  CONSTRAINT user_account_user_profile_fk2 FOREIGN KEY (user_profile_id) REFERENCES user_profile(id),
  PRIMARY KEY (user_account_id, user_profile_id)
);

INSERT INTO USER_PROFILE VALUES (1, 'ADMIN');
INSERT INTO USER_PROFILE VALUES (2, 'ISSUER');
INSERT INTO USER_PROFILE VALUES (3, 'RECEIVER');

INSERT INTO USER_ACCOUNT VALUES ('1', 'jdyanquen', '123', 'Jesus', 'Yanquen', 'jdyanquen@yopmail.com');
INSERT INTO USER_ACCOUNT VALUES ('2', 'dacorrea', '123', 'David', 'Correa', 'dacorrea@yopmail.com');
INSERT INTO USER_ACCOUNT VALUES ('3', 'latorres', '123', 'Laura', 'Torres', 'latorres@yopmail.com');

INSERT INTO USER_ACCOUNT_USER_PROFILE(user_account_id, user_profile_id) VALUES ('1', 1);
INSERT INTO USER_ACCOUNT_USER_PROFILE(user_account_id, user_profile_id) VALUES ('2', 2);
INSERT INTO USER_ACCOUNT_USER_PROFILE(user_account_id, user_profile_id) VALUES ('3', 3);


CREATE TYPE public."filter" AS (
	"fieldName" text,
	"filterOperator" text,
	value text);

CREATE TYPE public.page AS (
	"offset" text,
	"size" text);

CREATE TYPE public.sort AS (
	field text,
	direction text);

CREATE TYPE public.criteria AS (
	filters filter[],
	sorts sort[],
	page page);

CREATE OR REPLACE FUNCTION public.fn_build_where(p_filters filter[], p_field_to_alias JSON)
 RETURNS TEXT
 LANGUAGE plpgsql
AS $function$
DECLARE
	v_conditions TEXT := 'WHERE 1=1 ';
	v_filter FILTER;
	v_alias TEXT;
	v_operator_patterns JSON;
	v_pattern TEXT;
	v_value TEXT;

BEGIN
	
	v_operator_patterns := '{
		"EQ"  : " %s = %L ",
		"NE"  : " %s != %L ",
		"GT"  : " %s > %L ",
		"GE"  : " %s >= %L ",
		"LT"  : " %s < %L ",
		"LE"  : " %s <= %L ",
		"IN"  : " %s IS NULL ",
		"NN"  : " %s IS NOT NULL ",
		"SW"  : " %s LIKE %L ",
		"EW"  : " %s LIKE %L ",
		"CN"  : " %s LIKE %L ",
		"NC"  : " %s NOT LIKE %L ",
		"ANY" : " %s = ANY(%L) ",
		"REV" : " %L IN (%s) "
	}'::JSON;
	
	IF (p_filters IS NOT NULL) THEN
		FOREACH v_filter IN ARRAY p_filters
		LOOP
			SELECT p_field_to_alias ->> v_filter."fieldName"
			INTO v_alias;
			
			SELECT v_operator_patterns ->> v_filter."filterOperator"
			INTO v_pattern;
			
			v_value := TRIM(v_filter."value");
			
			IF v_filter."filterOperator" IN ('EW' ,'CN', 'NC') THEN
				v_value := '%' || v_value; 
			END IF;
		
			IF v_filter."filterOperator" IN ('SW' ,'CN', 'NC') THEN
				v_value := v_value || '%'; 
			END IF;
		
			IF v_alias IS NOT NULL AND v_pattern IS NOT NULL THEN
				IF (v_filter."filterOperator" = 'REV') THEN
					v_conditions := v_conditions || chr(13) || 'AND' || FORMAT(v_pattern, v_value, v_alias);
				ELSE
					v_conditions := v_conditions || chr(13) || 'AND' || FORMAT(v_pattern, v_alias, v_value);
				END IF;
			END IF;
		END LOOP;
	END IF;
	RETURN v_conditions;
END;
$function$
;

-- DROP FUNCTION public.find_posts(criteria);

CREATE OR REPLACE FUNCTION public.find_posts(criteria criteria)
 RETURNS TABLE(id bigint, created_at timestamp without time zone, score integer, summary text, views integer, voters_counter integer, created_by_user_account_id bigint, total_records integer)
 LANGUAGE plpgsql
AS $function$
DECLARE 
	v_query TEXT;
	v_select TEXT;
	v_from TEXT;
	v_where TEXT;
	v_order TEXT;
	v_limit_offset TEXT;
	
	v_count TEXT := 'SELECT COUNT(1)';
	v_total_records INTEGER := 0; 
	v_field_to_alias json;
	
BEGIN
	
	v_field_to_alias := '{
		"sumary"    : "p.summary",
		"score"     : "p.score",
		"createdAt" : "p.created_at"
	}'::json;
	
	v_select := chr(13) || 'SELECT 
		p.id, 
		p.created_at, 
		p.score, 
		p.summary::text, 
		p."views", 
		p.voters_counter, 
		p.created_by_user_account_id,
		%s AS records_count ';
	
	v_from := chr(13) || 'FROM public.post p ';

	v_where := chr(13) || fn_build_where(criteria.filters, v_field_to_alias);

	v_order := chr(13) || 'ORDER  BY p.created_at DESC ';

	v_query := v_count || v_from || v_where; 
	
	EXECUTE(v_query) INTO v_total_records;

	v_query := format(v_select, v_total_records) || v_from || v_where;

	RAISE NOTICE ' % ', v_query;
	
	RETURN query EXECUTE (v_query);

END;
$function$
;
 
SELECT public.find_posts('(\{\\\(sumary\\\,CN\\\,pring\\\)\,\\\(score\\\,GE\\\,4\\\)\,\\\(createdAt\\\,LT\\\,2024/08/17 11:57:07.210 -0500\\\)\},\{\\\(createdAt\\\,DESC\\\)\},\(0\,50\))');

