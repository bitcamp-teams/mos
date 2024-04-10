-- studyNo에 따라 커리큘럼 전체를 정렬하여 가져오기
SELECT title, ordr, layer, created_date
FROM curriculum
WHERE study_no = 1
ORDER BY ordr, layer, layer_ordr;

-- wikiNo에 따라 view에 필요한 정보를 가져오기
SELECT study_no, title, member_no, content_created_date, content_updated_date, content, likes
FROM curriculum
WHERE wiki_no = 1;
