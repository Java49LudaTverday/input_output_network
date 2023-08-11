package itelran.employees.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Organization {
	
	 public static  <T, K>  List<T> getByCategory(Map<K,Collection<T>> map, K category, Comparator<T> comp ) {		
		 return map.getOrDefault(category, Collections.emptyList()).stream()
			.sorted(comp).toList();
	 }
	
	public static <T, K> List<T> getByCategory(TreeMap<K,Collection<T>> map,
			            K from, K to, Comparator<T> comp) {
		return  map.subMap(from, true, to, true).values().stream()
				 .flatMap(col -> col.stream().sorted(comp))
				 .toList();					
	}
	
	public static  <K,T> void remove (Map<K, Collection<T>> map, K key, T object){
		Collection<T> collection = map.get(key);
		collection.remove(object);
		if(collection.isEmpty()) {
			map.remove(collection);
		}		
	}
	public static <K,T> void addToMap (Map<K, Collection<T>> map, K key,  T object){
		map.computeIfAbsent(key, k -> new HashSet<>()).add(object);
	}
}
