package com.inventory.inventory.common.redis;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inventory.inventory.common.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 封装redis 缓存服务器服务接口
 * @author lijunyu
 *
 */
@Service
public class RedisService{

	private static String redisCode = "utf-8";

	@Autowired
	private RedisTemplate redisTemplate;
	
	
	private RedisService() {

	}
	private static RedisService instance = new RedisService();

	public static RedisService getInstance() {
		return instance;
	}

	public long del(final String... keys) {
		// TODO Auto-generated method stub
		return (Long) redisTemplate.execute(new RedisCallback(){
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}

	public void set(final byte[] key, final byte[] value, final long liveTime) {
		// TODO Auto-generated method stub
		redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return 1L;
			}
		});
	}
	public <T> T getSet(final String key,final Object val) {
		// TODO Auto-generated method stub
		return (T) redisTemplate.execute(new RedisCallback<T>() {

			@Override
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				
				byte[] by = connection.getSet(key.getBytes(), SerializeUtil.serialize(val));
				return SerializeUtil.unserialize(by);
			}
			
		});
	}
	public Boolean setNX(final String key,final Object val) {
		// TODO Auto-generated method stub
		return (Boolean) redisTemplate.execute(new RedisCallback() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.setNX(key.getBytes(), SerializeUtil.serialize(val));
			}
		});
	}
	
	public void rPush(final String key,final Object val,final long liveTime) {
		// TODO Auto-generated method stub
		redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				resetLiveTime(key, liveTime);
				return connection.rPush(key.getBytes(), SerializeUtil.serialize(val));
			}
		});
	}
	
	public List<Map> lRange(final String key,final long start,final long end) {
		// TODO Auto-generated method stub
		return (List<Map>) redisTemplate.execute(new RedisCallback() {
			public List<Map> doInRedis(RedisConnection connection) throws DataAccessException {
				List<Map> result = new ArrayList<Map>();
				List<byte[]> list = connection.lRange(key.getBytes(), start, end);
				for(byte[] by:list){
					result.add((Map) SerializeUtil.unserialize(by));
				}
				return result;
			}
		});
	}
	
	public Long lSize(final String key) {
		// TODO Auto-generated method stub
		return (Long) redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lLen(key.getBytes());
			}
		});
	}

	public void setMap(final String  key,final Map value, final long liveTime) {
		redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] byteKey = key.getBytes();//非中文键
				byte[] byteValue = redisTemplate.getHashValueSerializer().serialize(value);
				connection.setEx(byteKey, liveTime, byteValue);
				return 1L;
			}
		});
	}
	
	public Map getMap(final String  key) {
		byte[] bytes =  (byte[])redisTemplate.execute(new RedisCallback() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
		return (Map) redisTemplate.getHashValueSerializer().deserialize(bytes);
	}
	
	
	
	public void setList(final String  key,final List value, final long liveTime) {
		redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] byteKey = key.getBytes();//非中文键
				byte[] byteValue = redisTemplate.getHashValueSerializer().serialize(value);
				connection.set(byteKey,byteValue);
				if (liveTime > 0) {
					connection.expire(byteKey, liveTime);
				}
				return 1L;
			}
		});
	}
	
	public List getList(final String  key) {
		byte[] bytes =  (byte[])redisTemplate.execute(new RedisCallback() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
		return (List) redisTemplate.getHashValueSerializer().deserialize(bytes);
	}

	public void set(String key, String value, long liveTime) {
		// TODO Auto-generated method stub
		this.set(key.getBytes(), value.getBytes(), liveTime);
	}
	
	public String hget(final String key,final String hashKey){
		String value = (String) redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] result = connection.hGet(hashKey.getBytes(), key.getBytes());
				if(result!=null){
					return new String(result);
				}else{
					return "";
				}
				
			}
		});
		
		return value;
	}


	public void set(String key, String value) {
		// TODO Auto-generated method stub
		this.set(key, value, 300);
	}


	public void set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		this.set(key, value, 300);
	}

	public <T> void setT(String key, T value) {
		setT(key.getBytes(),value,300);
	}
	public <T> void setT(String key, T value, long liveTime) {
		setT(key.getBytes(),value,liveTime);
	}

	public <T> void setT(final byte[] key, T value, long liveTime){
		set(key,SerializeUtil.serialize(value),liveTime);
	}

	public <T> T getT(String key){
		return (T) SerializeUtil.unserialize(get(key.getBytes()));
	}

	public byte[] get(final byte[] key) {
		// TODO Auto-generated method stub
		return (byte[])redisTemplate.execute(new RedisCallback() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key);
			}
		});
	}

	public String get(final String key) {
		// TODO Auto-generated method stub
		return (String)redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					if(connection.exists(key.getBytes())){
						return new String(connection.get(key.getBytes()), redisCode);
					}else{
						return "";
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return "";
			}
		});
	}


	public Set keys(String pattern) {
		// TODO Auto-generated method stub
		return redisTemplate.keys(pattern);
	}


	public boolean exists(final String key) {
		// TODO Auto-generated method stub
		return (Boolean)redisTemplate.execute(new RedisCallback() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}


	public String flushDB() {
		// TODO Auto-generated method stub
		return (String)redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}


	public long dbSize() {
		// TODO Auto-generated method stub
		return (Long)redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
	}


	public String ping() {
		// TODO Auto-generated method stub
		return (String)redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {

				return connection.ping();
			}
		});
	}


	public boolean resetLiveTime(String key){
		return resetLiveTime(key.getBytes(), 300);
	}

	public boolean resetLiveTime(String key,long liveTime){
		return resetLiveTime(key.getBytes(), liveTime);
	}

	public boolean resetLiveTime(final byte[] key,final long liveTime){
		return (Boolean)redisTemplate.execute(new RedisCallback() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.expire(key, liveTime);
			}
		});
	}

}
