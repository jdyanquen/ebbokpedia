

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

CREATE TYPE public.field_alias AS (
	field text,
	alias text);


CREATE OR REPLACE FUNCTION public.fn_get_alias_for_field(field_to_alias _field_alias, p_field text)
 RETURNS TEXT
 LANGUAGE plpgsql
AS $function$
DECLARE
	v_field_alias field_alias;
BEGIN
	FOREACH v_field_alias IN ARRAY field_to_alias
	LOOP
		IF (v_field_alias.field = p_field) THEN
			RETURN v_field_alias.alias;
		END IF;
	END LOOP;
	RAISE EXCEPTION 'There isn''t any alias for the given field'; 
END;
$function$
;

CREATE OR REPLACE FUNCTION public.fn_build_where(filters filter[], field_to_alias field_alias[])
 RETURNS TEXT
 LANGUAGE plpgsql
AS $function$
DECLARE
	v_conditions TEXT := 'WHERE 1=1 ';
	v_filter FILTER;
	v_alias TEXT;
BEGIN
	IF (filters IS NOT NULL) THEN
		FOREACH v_filter IN ARRAY filters
		LOOP
			IF v_filter.value IS NOT NULL AND TRIM(v_filter.value) <> '' THEN
				v_alias := fn_get_alias_for_field(field_to_alias, v_filter."fieldName");
				IF (v_filter."filterOperator" IN ('LIKE', 'NOT LIKE')) THEN
					v_conditions := v_conditions || chr(13) || FORMAT('AND %s %s ''%%%s%%''', v_alias, v_filter."filterOperator", TRIM(v_filter."value"));
				
				ELSIF (v_filter."filterOperator" = 'INVERSE_IN') THEN
					v_conditions := v_conditions || chr(13) || FORMAT('AND %L IN (%s)', TRIM(v_filter."value"), v_filter."filterOperator", v_alias);
				
				ELSE
					v_conditions := v_conditions || chr(13) || FORMAT('AND %s %s %L', v_alias, v_filter."filterOperator", TRIM(v_filter."value"));
				END IF;
			END IF;
		END LOOP;
	END IF;
	RETURN v_conditions;
END;
$function$
;


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
	v_field_to_alias field_alias[];
	
BEGIN
	
	v_field_to_alias := ARRAY[
		('sumary', 'p.summary'),
		('score', 'p.score'),
		('createdAt', 'p.created_at')
	];
	
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


SELECT public.find_posts('(\{\\\(sumary\\\,LIKE\\\,pring\\\)\,\\\(score\\\,>=\\\,4\\\)\,\\\(createdAt\\\,<\\\,2024/06/16 18:03:39.382 -0500\\\)\},\{\\\(createdAt\\\,DESC\\\)\},\(0\,50\))');

