--秒杀执行存储过程
-- row_count()返回上一条修改类型sql(delete,insert,update)的影响行数
DELIMITER $$ -- console ; 转换为$$
CREATE PROCEDURE `seckill`.`execute_seckill`
  (in v_seckill_id bigint, in v_phone bigint, in v_kill_time TIMESTAMP, out r_result INT)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION;
    INSERT ignore INTO success_killed (seckill_id, user_phone, create_time)
      VALUES (v_seckill_id, v_phone, v_kill_time);
    SELECT ROW_COUNT() INTO insert_count;
    if (insert_count = 0) THEN
      ROLLBACK;
      SET r_result = -1;
    elseif (insert_count < 0) THEN
      ROLLBACK;
      SET r_result = -2;
    else
      UPDATE seckill SET number = number - 1
        WHERE seckill_id = v_seckill_id /*AND end_time > v_kill_time AND start_time < v_kill_time*/ and number > 0;
      SELECT ROW_COUNT() INTO insert_count;
      if (insert_count = 0) THEN
        ROLLBACK;
        SET r_result = 0;
      elseif(insert_count < 0) THEN
        ROLLBACK;
        SET r_result = -2;
      else
        COMMIT;
        set r_result = 1;
      end if;
    END if;
  END;
$$
--存储过程定义结束
DELIMITER ;

SET @r_result = -3;
call execute_seckill(1003, 13501234567, now(), @r_result);
--获取结果
select @r_result



