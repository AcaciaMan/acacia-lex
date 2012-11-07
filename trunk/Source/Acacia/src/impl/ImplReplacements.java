/*
 * Copyright 2012 Acacia Man
 * The program is distributed under the terms of the GNU General Public License
 * 
 * This file is part of acacia-lex.
 *
 * acacia-lex is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * acacia-lex is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with acacia-lex.  If not, see <http://www.gnu.org/licenses/>.
 */ 
package impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImplReplacements {

    private StringBuilder source;
    int offset = 0;
    List<Point> lP = new ArrayList<Point>();
    Pattern patt;
    Pattern pattEnd;

    public ImplReplacements(StringBuilder source) {
        this.source = source;
    }

    public void initCommentsPatterns(String identifier) {
        String startComment = "//@" + identifier + "(.*?)^";
        String endComment = "^(.*)//\\\\@" + identifier;

        patt = Pattern.compile(startComment, Pattern.DOTALL | Pattern.MULTILINE);
        pattEnd = Pattern.compile(endComment, Pattern.MULTILINE);

    }

    public String getFragment(String identifier) {
        String result = null;

        initCommentsPatterns(identifier);

        Matcher m = patt.matcher(source);
        Matcher mEnd = pattEnd.matcher(source);

        m.find();
        mEnd.find();

        result = source.substring(m.end(), mEnd.start());
        return result;
    }

    public void replace(Point p, String replacement) {
        source.replace(offset + p.x, offset + p.y, replacement);
        offset += replacement.length() - (p.y - p.x);
    }

    /**
     * @return the source
     */
    public StringBuilder getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(StringBuilder source) {
        this.source = source;
    }

    public void init() {
        lP = new ArrayList<Point>();
        offset = 0;
    }

    public void replaceAll(String identifier, String replacement) {

        init();

        Pattern pattern = Pattern.compile(identifier);
        Matcher m = pattern.matcher(source);
        while (m.find()) {
            lP.add(new Point(m.start(), m.end()));
        }

        for (Point p : lP) {
            this.replace(p, replacement);
        }
    }

    public void replaceAllIdentified(String identifier, String replacement) {

        init();
        initCommentsPatterns(identifier);

        Matcher m = patt.matcher(source);
        Matcher mEnd = pattEnd.matcher(source);

        while (m.find()) {
            mEnd.find();
            lP.add(new Point(m.end(), mEnd.start()));
        }

        for (Point p : lP) {
            this.replace(p, replacement);
        }
    }
}
