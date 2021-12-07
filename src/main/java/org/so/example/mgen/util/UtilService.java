package org.so.example.mgen.util;

import org.springframework.stereotype.Service;

@Service
public class UtilService {
    
    public String removeLastChar(String s) {
		return (s == null || s.length() == 0)
				? null
				: (s.substring(0, s.length() - 1));
	}

}
