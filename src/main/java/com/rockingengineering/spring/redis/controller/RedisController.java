/**
 * 
 */
package com.rockingengineering.spring.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rockingengineering.spring.redis.service.RedisService;

/**
 * @author naveen
 *
 * @date 16-Oct-2019
 */
@RestController
@RequestMapping("")
public class RedisController {

	@Autowired
	private RedisService<String> redisService;

	@PostMapping("value/add")
	public ResponseEntity<String> addValue(@RequestParam String key, @RequestParam String value) {

		redisService.putValue(key, value);

		return ResponseEntity.ok("Added Key " + key + " With Value " + value);
	}

	@GetMapping("value/get/{key}")
	public ResponseEntity<String> getValue(@PathVariable String key) {

		return ResponseEntity.ok("Found Value " + redisService.getValue(key) + " for key " + key);
	}

	@PostMapping("hash/add")
	public ResponseEntity<String> addValueInHash(
			@RequestParam String hash, @RequestParam String key, @RequestParam String value) {

		redisService.putMap(hash, key, value);

		return ResponseEntity.ok("Added Key " + key + " With Value " + value + " in Hash " + hash);
	}

	@GetMapping("hash/get/{hash}/{key}")
	public ResponseEntity<String> getValueFromHash(@PathVariable String hash, @PathVariable String key) {

		return ResponseEntity.ok("Found Value " + redisService.getMapAsSingleEntry(hash, key) + " for key " + key + " in Hash " + hash);
	}

	@GetMapping("hash/get/all/{hash}")
	public ResponseEntity<String> getAllValueFromHash(@PathVariable String hash) {

		return ResponseEntity.ok("Found Value " + redisService.getMapAsAll(hash) + " in Hash " + hash);
	}

}