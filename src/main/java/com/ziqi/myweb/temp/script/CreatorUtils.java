package com.ziqi.myweb.temp.script;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * Description: CreatorUtils
 * User: qige
 * Date: 15/4/12
 * Time: 13:10
 */
public class CreatorUtils {

    public static String getSimpleName(Class clazz) {
        return clazz.getSimpleName();
    }

    public static String getClassName(Class clazz) {
        return clazz.getName();
    }

    public static String getQueryName(Class clazz) {
        return clazz.getSimpleName().replace("DO", "Query");
    }

    public static String getQueryClass(Class clazz) {
        return clazz.getName().replace("DO", "Query").replace("dal", "common").replace("model", "query");
    }

    public static String getDTOClass(Class clazz) {
        return clazz.getName().replace("DO", "DTO").replace("dal", "common");
    }

    public static String getDTOName(Class clazz) {
        return clazz.getSimpleName().replace("DO", "DTO");
    }

    public static String getDAOName(Class clazz) {
        return clazz.getSimpleName().replace("DO", "DAO");
    }

    public static String getDAOClass(Class clazz) {
        return clazz.getName().replace("DO", "DAO").replace("model", "dao");
    }

    public static String getVar(String temp) {
        StringBuilder sb = new StringBuilder(temp);
        sb.replace(0, 1, Character.toLowerCase(temp.charAt(0)) + "");
        return sb.toString();
    }

    public static String getFirstCharUp(String temp) {
        StringBuilder sb = new StringBuilder(temp);
        sb.replace(0, 1, Character.toUpperCase(temp.charAt(0)) + "");
        return sb.toString();
    }

    public static String getServiceName(Class clazz) {
        return clazz.getSimpleName().replace("DO", "Service");
    }

    public static String getServiceClass(Class clazz) {
        return clazz.getName().replace("DO", "Service").replace("dal", "core").replace("model", "service");
    }

    public static String getBizName(Class clazz) {
        return clazz.getSimpleName().replace("DO", "Biz");
    }

    public static String getBizClass(Class clazz) {
        return clazz.getName().replace("DO", "Biz").replace("dal", "web").replace("model", "biz");
    }

    public static String buildInsert(Class clazz) {

        return  "\t<insert id=\"" + getDAOName(clazz) + ".insert\" parameterClass=\"" + getSimpleName(clazz) + "\"> \n" +
                "\t\tinsert into " + getSimpleName(clazz).replace("DO", "").toLowerCase() + " \n" +
                buildColumnNames(clazz) + "\n" +
                "\t\tVALUES\n" +
                buildJavaNames(clazz) + "\n" +
                "\t\t<selectKey resultClass=\"int\" keyProperty=\"id\"> \n" +
                "\t\t\tSELECT LAST_INSERT_ID() \n" +
                "\t\t</selectKey>  \n" +
                "\t</insert> \n";
    }

    public static String buildTypeAlias(Class clazz) {
        return  "\t<typeAlias alias=\"" + getSimpleName(clazz) + "\" type=\"" + getClassName(clazz) + "\"/> \n";
    }

    public static Set<String> selectSkip = new HashSet<String>(){
        {
            add("isDeleted");
            add("gmtCreate");
            add("gmtModified");
        }
    };

    public static Set<String> insertSkip = new HashSet<String>(){
        {
            add("id");
            add("version");
        }
    };

    public static Map<String, String> insertReplace = new HashMap<String, String>(){
        {
            put("isDeleted", "0");
            put("gmtCreate", "now()");
            put("gmtModified", "now()");
        }
    };

    public static Set<String> updateSkip = new HashSet<String>(){
        {
            add("id");
            add("gmtCreate");
            add("gmtModified");
            add("version");
            add("isDeleted");
        }
    };

    public static String buildColumnNames(Class clazz) {
        Class superClazz = clazz.getSuperclass();
        StringBuilder columnNames = new StringBuilder("");
        columnNames.append("\t\t(");
        for (Field field : superClazz.getDeclaredFields()) {
            if(insertSkip.contains(field.getName()))
                continue;
            columnNames.append("\t\t\t").append(toColumnName(field.getName())).append(",\n");
        }
        for (Field field : clazz.getDeclaredFields()) {
            columnNames.append("\t\t\t").append(toColumnName(field.getName())).append(",\n");
        }
        columnNames.replace(columnNames.length() - 2, columnNames.length() - 1, "");
        columnNames.append("\t\t)");
        return columnNames.toString();
    }

    public static String toColumnName(String javaName) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < javaName.length(); ++i) {
            if (Character.isUpperCase(javaName.charAt(i))) {
                res.append("_").append(Character.toLowerCase(javaName.charAt(i)));
            } else {
                res.append(Character.toLowerCase(javaName.charAt(i)));
            }
        }
        return res.toString();
    }

    public static String buildJavaNames(Class clazz) {
        Class superClazz = clazz.getSuperclass();
        StringBuilder javaNames = new StringBuilder("");
        javaNames.append("\t\t(");
        for (Field field : superClazz.getDeclaredFields()) {
            if(insertSkip.contains(field.getName()))
                continue;
            if(insertReplace.containsKey(field.getName())) {
                javaNames.append("\t\t\t").append(insertReplace.get(field.getName())).append(",\n");
            } else {
                javaNames.append("\t\t\t#").append(field.getName()).append("#,\n");
            }
        }
        for (Field field : clazz.getDeclaredFields()) {
            javaNames.append("\t\t\t#").append(field.getName()).append("#,\n");
        }
        javaNames.replace(javaNames.length() - 2, javaNames.length() - 1, "");
        javaNames.append("\t\t)");
        return javaNames.toString();
    }

    public static String buildResultItems(Class clazz) {
        StringBuilder items = new StringBuilder();
        Class superClazz = clazz.getSuperclass();
        for (Field field : superClazz.getDeclaredFields()) {
            items.append("\t\t<result property=\"").append(field.getName()).append("\" column=\"").append(toColumnName(field.getName())).append("\"/> \n");
        }
        for (Field field : clazz.getDeclaredFields()) {
            items.append("\t\t<result property=\"").append(field.getName()).append("\" column=\"").append(toColumnName(field.getName())).append("\"/> \n");
        }
        return items.toString();
    }

    public static String buildResultMap(Class clazz) {
        return  "\t<resultMap id=\"" + getSimpleName(clazz) + "Result\" class=\"" + getSimpleName(clazz) + "\"> \n" +
                buildResultItems(clazz) +
                "\t</resultMap> \n";
    }

    public static String buildDelete(Class clazz) {
        return  "\t<delete id=\"" + getDAOName(clazz) + ".delete\" parameterClass=\"int\"> \n" +
                "\t\tupdate " + toTableName(clazz) + " set is_deleted = 1 where id = #id# \n" +
                "\t</delete> \n";
    }

    public static String toTableName(Class clazz) {
        return getSimpleName(clazz).replace("DO", "").toLowerCase();
    }

    public static String buildSelect(Class clazz) throws Exception {
        StringBuilder condition = new StringBuilder();
        Class superClazz = clazz.getSuperclass();
        for (Field field : superClazz.getDeclaredFields()) {
            if(selectSkip.contains(field.getName()))
                continue;
            condition.append("\t\t\t<isNotNull property=\"").append(field.getName()).append("\" prepend=\"and\">\n");
            condition.append("\t\t\t\t<![CDATA[ ").append(toColumnName(field.getName())).append(" = #").append(field.getName()).append("# ]]>\n");
            condition.append("\t\t\t</isNotNull>\n");
        }
        for (Field field : clazz.getDeclaredFields()) {
            condition.append("\t\t\t<isNotNull property=\"").append(field.getName()).append("\" prepend=\"and\">\n");
            condition.append("\t\t\t\t<![CDATA[ ").append(toColumnName(field.getName())).append(" = #").append(field.getName()).append("# ]]>\n");
            condition.append("\t\t\t</isNotNull>\n");
        }

        return  "\t<select id=\"" + getDAOName(clazz) + ".select\" resultMap=\"" + getSimpleName(clazz) + "Result\" parameterClass=\"java.util.HashMap\"> \n" +
                "\t\tselect * from " + toTableName(clazz) + " where is_deleted = 0 \n" +
                "\t\t<dynamic prepend=\"and\"> \n" +

                condition.toString() +

                "\t\t\t<isNotEmpty property=\"fromCreate\" prepend=\"and\">\n" +
                "\t\t\t\t<![CDATA[ gmt_create > #fromCreate# ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t\t<isNotEmpty property=\"toCreate\" prepend=\"and\">\n" +
                "\t\t\t\t<![CDATA[ gmt_create < #toCreate# ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t\t<isNotEmpty property=\"fromModified\" prepend=\"and\">\n" +
                "\t\t\t\t<![CDATA[ gmt_modified > #fromModified# ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t\t<isNotEmpty property=\"toModified\" prepend=\"and\">\n" +
                "\t\t\t\t<![CDATA[ gmt_modified < #toModified# ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t</dynamic> \n" +

                "\t\t<dynamic> \n" +
                "\t\t\t<isNotEmpty property=\"orderField\">\n" +
                "\t\t\t\t<![CDATA[ order by $orderField$ ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t\t<isNotEmpty property=\"groupField\">\n" +
                "\t\t\t\t<![CDATA[ group by $groupField$ ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t</dynamic> \n" +
                "\t\tlimit #start#,#limit#\n" +
                "\t</select> \n" +

                "\t<select id=\"" + getDAOName(clazz) + ".selectById\" resultMap=\"" + getSimpleName(clazz) + "Result\" parameterClass=\"int\"> \n" +
                "\t\tselect * from " + toTableName(clazz) + " where \n" +
                "\t\tis_deleted = 0 and id = #id# limit 1 \n" +
                "\t</select> \n"+

                "\t<select id=\"" + getDAOName(clazz) + ".selectCount\" resultClass=\"int\" parameterClass=\"java.util.HashMap\"> \n" +
                "\t\tselect count(id) from " + toTableName(clazz) + " where is_deleted = 0 \n" +
                "\t\t<dynamic prepend=\"and\"> \n" +

                condition.toString() +

                "\t\t\t<isNotEmpty property=\"fromCreate\" prepend=\"and\">\n" +
                "\t\t\t\t<![CDATA[ gmt_create > #fromCreate# ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t\t<isNotEmpty property=\"toCreate\" prepend=\"and\">\n" +
                "\t\t\t\t<![CDATA[ gmt_create < #toCreate# ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t\t<isNotEmpty property=\"fromModified\" prepend=\"and\">\n" +
                "\t\t\t\t<![CDATA[ gmt_modified > #fromModified# ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +
                "\t\t\t<isNotEmpty property=\"toModified\" prepend=\"and\">\n" +
                "\t\t\t\t<![CDATA[ gmt_modified < #toModified# ]]>\n" +
                "\t\t\t</isNotEmpty>\n" +

                "\t\t</dynamic> \n" +
                "\t</select> \n";
    }

    public static String buildUpdate(Class clazz) {
        StringBuilder condition = new StringBuilder();
        Class superClass = clazz.getSuperclass();
        for (Field field : superClass.getDeclaredFields()) {
            if(updateSkip.contains(field.getName()))
                continue;
            condition.append("\t\t\t<isNotNull property=\"").append(field.getName()).append("\" prepend=\"and\">\n");
            condition.append("\t\t\t\t<![CDATA[ ").append(toColumnName(field.getName())).append(" = #").append(field.getName()).append("# ]]>\n");
            condition.append("\t\t\t</isNotNull>\n");
        }
        for (Field field : clazz.getDeclaredFields()) {
            condition.append("\t\t\t<isNotNull property=\"").append(field.getName()).append("\" prepend=\"and\">\n");
            condition.append("\t\t\t\t<![CDATA[ ").append(toColumnName(field.getName())).append(" = #").append(field.getName()).append("# ]]>\n");
            condition.append("\t\t\t</isNotNull>\n");
        }

        return  "\t<update id=\"" + getDAOName(clazz) + ".update\" parameterClass=\"" + getSimpleName(clazz) + "\"> \n" +
                "\t\tupdate " + toTableName(clazz) + " set version = version + 1 and gmt_modified = now()\n" +
                "\t\t<dynamic> \n" +
                condition.toString() +
                "\t\t</dynamic> \n" +
                "\t\twhere id = #id# and version = #version# \n" +
                "\t</update> \n";
    }

    public static String buildHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE sqlMap PUBLIC \"-//iBATIS.com//DTD SQL Map 2.0//EN\"\n " +
                "\t\"http://www.ibatis.com/dtd/sql-map-2.dtd\">\n";
    }

    public static void buildInitSQL(Class[] classes) throws IOException {
        String path = "myweb/src/main/resources/init.sql";
        FileWriter writer = new FileWriter(path);
        for(Class clazz : classes) {
            String item = "";
            Class superClazz = clazz.getSuperclass();
            for (Field field : superClazz.getDeclaredFields()) {
                if(field.getName().equals("id")) {
                    item += "\t" + toColumnName(field.getName()) + " " + getDataType(field) + " not null auto_increment primary key,\n";
                } else if(field.getName().equals("version")) {
                    item += "\t" + toColumnName(field.getName()) + " " + getDataType(field) + " not null default '0',\n";
                } else {
                    item += "\t" + toColumnName(field.getName()) + " " + getDataType(field) + ",\n";
                }
            }
            for (Field field : clazz.getDeclaredFields()) {
                item += "\t" + toColumnName(field.getName()) + " " + getDataType(field) + ",\n";
            }
            item = item.substring(0, item.length() - 2);
            writer.write("drop table " + toTableName(clazz) + ";\n");
            writer.write("create table " + toTableName(clazz) + "(\n" + item + "\n);\n\n");
        }
        writer.close();
    }

    private static Map<Class, String> convert = new HashMap<Class, String>(){
        {
            put(Integer.class, "INTEGER");
            put(Date.class, "DATETIME");
            put(String.class, "VARCHAR(100)");
        }
    };
    private static String getDataType(Field field) {
        return convert.get(field.getType());
    }

    public static void buildQueryClass(Class[] classes) throws IOException {
        String dir = "myweb/src/main/java/";
        for(Class clazz : classes) {
            File srcFile = new File(dir + getClassName(clazz).replace(".", "/") + ".java");
            File tarFile = new File(dir + getQueryClass(clazz).replace(".", "/") + ".java");
            fileChannelCopy(srcFile, tarFile);

            String data = "";
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(tarFile));
            while((line = reader.readLine()) != null) {
                data += line + "\n";
            }
            data = data.replace("dal.model", "common.query");
            data = data.replace("DO", "Query");
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(tarFile));
            writer.write(data);
            writer.close();
        }
    }

    public static void buildDTOClass(Class[] classes) throws IOException {
        String dir = "myweb/src/main/java/";
        for(Class clazz : classes) {
            File srcFile = new File(dir + getClassName(clazz).replace(".", "/") + ".java");
            File tarFile = new File(dir + getDTOClass(clazz).replace(".", "/") + ".java");
            fileChannelCopy(srcFile, tarFile);

            String data = "";
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(tarFile));
            while((line = reader.readLine()) != null) {
                data += line + "\n";
            }
            data = data.replace("dal.model", "common.model");
            data = data.replace("DO", "DTO");
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(tarFile));
            writer.write(data);
            writer.close();
        }
    }

    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void buildConstantsClass(Class[] classes) throws IOException {
        String path = "myweb/src/main/java/com/ziqi/myweb/common/constants/TableConstants.java";
        FileWriter writer = new FileWriter(path);
        writer.write("package com.ziqi.myweb.common.constants;\n" +
                    "\n" +
                    "/**\n" +
                    " * Description: TableConstants\n" +
                    " * User: qige\n" +
                    " * Date: 15/4/11\n" +
                    " * Time: 01:22\n" +
                    " */\n" +
                    "public class TableConstants {\n" +
                    "\n");
        for(Class clazz : classes) {
            writer.write("    public static class " + getSimpleName(clazz).replace("DO", "") + " {\n");
            for(Field field : clazz.getDeclaredFields()) {
                writer.write("      public static String " + field.getName() + " = \"" + toColumnName(field.getName()) + "\";\n");
            }
            writer.write("    }\n");
        }

        writer.write("}\n");
        writer.close();
    }

    public static void buildDAOClass(Class[] classes) throws IOException {
        String dir = "myweb/src/main/java/";
        for(Class clazz : classes) {
            String queryClass = getDAOClass(clazz).replace(".", "/");
            FileWriter writer = new FileWriter(dir + queryClass + ".java");

            writer.write("package com.ziqi.myweb.dal.dao;\n" +
                    "\n" +
                    "import com.ziqi.myweb.dal.model." + getSimpleName(clazz) + ";\n" +
                    "\n" +
                    "/**\n" +
                    " * Description: " + getDAOName(clazz) + "\n" +
                    " * User: qige\n" +
                    " * Date: 15/4/11\n" +
                    " * Time: 01:22\n" +
                    " */\n" +
                    "public class " + getDAOName(clazz) + " extends BaseDAO<" + getSimpleName(clazz) + "> {\n" +
                    "    public " + getDAOName(clazz) + "() {\n" +
                    "        super(" + getDAOName(clazz) + ".class);\n" +
                    "    }\n" +
                    "}\n");
            writer.close();
        }
    }

    public static void buildServiceClass(Class[] classes) throws IOException {
        String dir = "myweb/src/main/java/";
        for(Class clazz : classes) {
            String queryClass = getServiceClass(clazz).replace(".", "/");
            FileWriter writer = new FileWriter(dir + queryClass + ".java");

            writer.write("package com.ziqi.myweb.core.service;\n" +
                    "\n" +
                    "import com.ziqi.myweb.dal.model." + getSimpleName(clazz) + ";\n" +
                    "import com.ziqi.myweb.common.model." + getDTOName(clazz) + ";\n" +
                    "import org.slf4j.LoggerFactory;\n" +
                    "\n" +
                    "import java.util.List;\n" +
                    "import java.util.ArrayList;\n" +
                    "\n" +
                    "/**\n" +
                    " * Description: " + getServiceName(clazz) + "\n" +
                    " * User: qige\n" +
                    " * Date: 15/4/11\n" +
                    " * Time: 01:22\n" +
                    " */\n" +
                    "public class " + getServiceName(clazz) + " extends BaseService<" + getDTOName(clazz) + ", "  + getSimpleName(clazz) + "> {\n" +
                    "\n" +
                    "    public " + getServiceName(clazz) + "() {\n" +
                    "        setLogger(LoggerFactory.getLogger(" + getServiceName(clazz) + ".class));\n" +
                    "    }\n" +
                    "\n" +
                    buildConvertCode(clazz) +
                    "}\n");
            writer.close();
        }
    }

    public static void buildBizClass(Class[] classes) throws IOException {
        String dir = "myweb/src/main/java/";
        for(Class clazz : classes) {
            String bizClass = getBizClass(clazz).replace(".", "/");
            FileWriter writer = new FileWriter(dir + bizClass + ".java");

            writer.write("package com.ziqi.myweb.web.biz;\n" +
                        "\n" +
                        "import com.ziqi.myweb.dal.model." + getSimpleName(clazz) + ";\n" +
                        "\n" +
                        "\n" +
                        "/**\n" +
                        " * Description: " + getBizName(clazz) + "\n" +
                        " * User: qige\n" +
                        " * Date: 15/4/16\n" +
                        " * Time: 22:11\n" +
                        " */\n" +
                        "public class " + getBizName(clazz) + " extends BaseBiz<" + getSimpleName(clazz) + "> {\n" +
                        "}\n");
            writer.close();
        }
    }

    public static void  buildSQL(Class[] classes) throws Exception {
        String dir = "myweb/src/main/resources/ibatis/";
        for(Class clazz : classes) {
            FileWriter writer = new FileWriter(dir + clazz.getSimpleName().replace("DO", "").toLowerCase() + "-sqlmap.xml");
            writer.write(buildHeader());
            writer.write("<sqlMap> \n");
            writer.write(buildTypeAlias(clazz));
            writer.write(buildResultMap(clazz));
            writer.write(buildSelect(clazz));
            writer.write(buildInsert(clazz));
            writer.write(buildUpdate(clazz));
            writer.write(buildDelete(clazz));
            writer.write("</sqlMap> \n");
            writer.close();
        }
        buildInitSQL(classes);
    }

    public static String buildConvertCode(Class clazz) {
        String code = "";
        code += "    @Override\n";
        code += "    public " + getDTOName(clazz) + " DOToDTO(" + getSimpleName(clazz) + " " + getVar(getSimpleName(clazz)) + ") {\n";
        code += "        " + getDTOName(clazz) + " " +  getVar(getDTOName(clazz)) + " = new " + getDTOName(clazz) + "();\n";
        for(Field field : clazz.getSuperclass().getDeclaredFields()) {
            code += "        " +  getVar(getDTOName(clazz)) + ".set" + getFirstCharUp(field.getName()) + "(" +  getVar(getSimpleName(clazz)) + ".get" + getFirstCharUp(field.getName()) + "());\n";
        }
        for(Field field : clazz.getDeclaredFields()) {
            code += "        " +  getVar(getDTOName(clazz)) + ".set" + getFirstCharUp(field.getName()) + "(" +  getVar(getSimpleName(clazz)) + ".get" + getFirstCharUp(field.getName()) + "());\n";
        }
        code += "        return " +  getVar(getDTOName(clazz)) + ";\n";
        code += "    }\n";
        code += "    @Override\n";
        code += "    public " + getSimpleName(clazz) + " DTOToDO(" + getDTOName(clazz) + " " + getVar(getDTOName(clazz)) + ") {\n";
        code += "        " + getSimpleName(clazz) + " " +  getVar(getSimpleName(clazz)) + " = new " + getSimpleName(clazz) + "();\n";
        for(Field field : clazz.getSuperclass().getDeclaredFields()) {
            code += "        " +  getVar(getSimpleName(clazz)) + ".set" + getFirstCharUp(field.getName()) + "(" +  getVar(getDTOName(clazz)) + ".get" + getFirstCharUp(field.getName()) + "());\n";
        }
        for(Field field : clazz.getDeclaredFields()) {
            code += "        " +  getVar(getSimpleName(clazz)) + ".set" + getFirstCharUp(field.getName()) + "(" +  getVar(getDTOName(clazz)) + ".get" + getFirstCharUp(field.getName()) + "());\n";
        }
        code += "        return " +  getVar(getSimpleName(clazz)) + ";\n";
        code += "    }\n";
        code += "    @Override\n";
        code += "    public List<" + getDTOName(clazz) + "> DOsToDTOs(List<" + getSimpleName(clazz) + "> " + getVar(getSimpleName(clazz)) + "s) {\n";
        code += "        List<" + getDTOName(clazz) + "> " +  getVar(getDTOName(clazz)) + "s = new ArrayList<" + getDTOName(clazz) + ">();\n";
        code += "        for("+getSimpleName(clazz)+" "+getVar(getSimpleName(clazz))+" : " + getVar(getSimpleName(clazz)) + "s) {\n";
        code += "            " + getVar(getDTOName(clazz)) + "s.add(DOToDTO("+getVar(getSimpleName(clazz))+"));\n";
        code += "        }\n";
        code += "        return " + getVar(getDTOName(clazz)) + "s;\n";
        code += "    }\n";
        code += "    @Override\n";
        code += "    public List<" + getSimpleName(clazz) + "> DTOsToDOs(List<" + getDTOName(clazz) + "> " + getVar(getDTOName(clazz)) + "s) {\n";
        code += "        List<" + getSimpleName(clazz) + "> " +  getVar(getSimpleName(clazz)) + "s = new ArrayList<" + getSimpleName(clazz) + ">();\n";
        code += "        for("+getDTOName(clazz)+" "+getVar(getDTOName(clazz)) + " : " + getVar(getDTOName(clazz)) + "s) {\n";
        code += "            " + getVar(getSimpleName(clazz)) + "s.add(DTOToDO("+getVar(getDTOName(clazz))+"));\n";
        code += "        }\n";
        code += "        return " + getVar(getSimpleName(clazz)) + "s;\n";
        code += "    }\n";

        return code;

    }

    public static void main(String[] args) {

    }

}
