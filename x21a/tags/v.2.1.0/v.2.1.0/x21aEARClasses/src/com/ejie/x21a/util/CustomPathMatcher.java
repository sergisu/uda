/*
* Copyright 2011 E.J.I.E., S.A.
*
* Licencia con arreglo a la EUPL, Versión 1.1 exclusivamente (la «Licencia»);
* Solo podrá usarse esta obra si se respeta la Licencia.
* Puede obtenerse una copia de la Licencia en
*
* http://ec.europa.eu/idabc/eupl.html
*
* Salvo cuando lo exija la legislación aplicable o se acuerde por escrito,
* el programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL»,
* SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas.
* Véase la Licencia en el idioma concreto que rige los permisos y limitaciones
* que establece la Licencia.
*/
package com.ejie.x21a.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * CustomPathMatcher generated by UDA
* @author UDA
*/
public class CustomPathMatcher implements PathMatcher {
    private PathMatcher target;

    public CustomPathMatcher() {
        target = new AntPathMatcher();
    }

    public String combine(String pattern1, String pattern2) {
        return target.combine(pattern1, pattern2); 
    }

    public String extractPathWithinPattern(String pattern, String path) {
        if (isEncoded(pattern)) {
            pattern = resolvePattern(pattern);
            if (pattern == null) return "";
        }
        return target.extractPathWithinPattern(pattern, path);
    }

    public Map<String, String> extractUriTemplateVariables(String pattern,
            String path) {
        if (isEncoded(pattern)) {
            pattern = resolvePattern(pattern);
            if (pattern == null) return Collections.emptyMap();
        }
        return target.extractUriTemplateVariables(pattern, path);
    }

    public Comparator<String> getPatternComparator(String pattern) {
        final Comparator<String> targetComparator = target.getPatternComparator(pattern);
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                if (isEncoded(o1)) {
                    if (isEncoded(o2)) {
                        return 0;
                    } else {
                        return -1;
                    }
                } else if (isEncoded(o2)) {
                    return 1;
                }
                return targetComparator.compare(o1, o2);
            }        
        };
    }

    public boolean isPattern(String pattern) {
        if (isEncoded(pattern)) {
            pattern = resolvePattern(pattern);
            if (pattern == null) return true;
        }
        return target.isPattern(pattern);
    }

    @Override
    public boolean match(String pattern, String path) {
        return pattern.endsWith(".*") ? false : target.match(pattern, path);
    }


    public boolean matchStart(String pattern, String path) {
        if (isEncoded(pattern)) {
            pattern = resolvePattern(pattern);
            if (pattern == null) return false;
        }
        return target.match(pattern, path);
    }

    private boolean isEncoded(String pattern) {
        return pattern != null && pattern.contains("$");
    }

    private String resolvePattern(String pattern) {
        int i = pattern.indexOf('$');
        if (i < 0) return pattern;
        else if (i == pattern.length() - 1) {
            return pattern.substring(0, i);
        } else {
            String tail = pattern.substring(i + 1);
            if (tail.startsWith(".")) return null;
            else return pattern.substring(0, i) + tail;
        }
    }
}
