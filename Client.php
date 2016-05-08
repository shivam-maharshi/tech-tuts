<?php

// Connection constants
define('MEMCACHED_HOST', '127.0.0.1');
define('MEMCACHED_PORT', '11211');

// Create connection
$memcache = new Memcache;
$cacheAvailable = $memcache->connect(MEMCACHED_HOST, MEMCACHED_PORT);

if(!cacheAvailable) {
	echo("Memcached server is not running. Make sure the server is up and running!");
} else {
	$memcache->set($key, $data);
}

?>