package server;

public class Сonstant
{
    public static final String  catDccrdkndstIdPath = "catDccrdkndstIdPath";
    public static final String  catRoot = "catRoot";
    public static final String  catDccrdkndstId = "catDccrdkndstId";
    
    public static final String  catName = "catName";
    public static final String  catIdentifier = "catIdentifier";
    public static final String  catDescription = "catDescription";
    
    public static final String  catOrderNumber = "catOrderNumber";
    public static final String  catKindId = "catKindId";
    public static final String  catParentId = "catParentId";

    public static final String  catBaseId = "catBaseId";
    public static final String  catMaximumInstances = "catMaximumInstances";
    public static final String  catGroupsInd = "catGroupsInd";

    public static final String  catGroupsId = "catGroupsId";
    public static final String  catGroupsName = "catGroupsName";
    public static final String  catKindsInd = "catKindsInd";

    public static final String  catKindsId = "catKindsId";
    public static final String  catKindsName = "catKindsName";

    public final static String url = "jdbc:oracle:thin:@10.100.22.9:1521:HPDOILDV";
    public final static String username = "ADMIN";
    public final static String password = "admin";

    public final static String solrCoreUrl = "http://localhost:8983/solr/DOC_STRUCTURES";
    
    public final static String sqlQuery = "SELECT \r\n" + 
        "    sys_connect_by_path (dcks.DCCRDKNDST_ID,\r\n" + 
        "    '/') DCCRDKNDST_ID_PATH,\r\n" + 
        "    Connect_By_Root(DCCRDKNDST_ID) root,\r\n" + 
        "    dcks.DCCRDKNDST_ID,\r\n" + 
        "    dcks.NAME,\r\n" + 
        "    dcks.IDENTIFIER,\r\n" + 
        "    dcks.DESCRIPTION,\r\n" + 
        "    dcks.ORDER_NUMBER,\r\n" + 
        "    dcks.DCCRDKND_DCCRDKND_ID KIND_ID,\r\n" + 
        "    dcks.DCCRDKNDST_DCCRDKNDST_ID PARENT_ID,\r\n" + 
        "    dcks.DCCRDKND_DCCRDKND_BASE_ID,\r\n" + 
        "    dcks.MAXIMUM_INSTANCES,\r\n" + 
        "    dcga.IDENTIFIER GROUPS_IND,\r\n" + 
        "    dcga.DCCRDGRP_ID GROUPS_ID,\r\n" + 
        "    dcga.NAME GROUPS_NAME,\r\n" + 
        "    dcka.IDENTIFIER KINDS_IND,\r\n" + 
        "    dcka.DCCRDKND_ID KINDS_ID,\r\n" + 
        "    dcka.NAME KINDS_NAME\r\n" + 
        "FROM\r\n" + 
        "    DOC_CARD_KIND_STRUCTURES dcks\r\n" + 
        "JOIN DOC_CARD_KINDS_APP dcka\r\n" + 
        "    ON\r\n" + 
        "    DCKS.DCCRDKND_DCCRDKND_ID = dcka.DCCRDKND_ID\r\n" + 
        "JOIN DOC_BASE.DOC_CARD_GROUPS_APP dcga \r\n" + 
        "    ON\r\n" + 
        "    dcka.DCCRDGRP_DCCRDGRP_ID = dcga.DCCRDGRP_ID\r\n" + 
        "START WITH\r\n" + 
        "    dcks.DCCRDKNDST_ID IN (\r\n" + 
        "    SELECT \r\n" + 
        "        DCCRDKNDST_ID\r\n" + 
        "    FROM\r\n" + 
        "        DOC_CARD_KIND_STRUCTURES\r\n" + 
        "    JOIN DOC_BASE.DOC_CARD_KINDS_APP dcka2 \r\n" + 
        "        ON\r\n" + 
        "        DCCRDKND_DCCRDKND_ID = dcka2.DCCRDKND_ID\r\n" + 
        "    JOIN DOC_BASE.DOC_CARD_GROUPS_APP dcga2 \r\n" + 
        "        ON\r\n" + 
        "        dcka2.DCCRDGRP_DCCRDGRP_ID = dcga2.DCCRDGRP_ID\r\n" + 
        "    WHERE\r\n" + 
        "        dcga2.IDENTIFIER = 'ПРОЕКТНЫЕ ДОКУМЕНТЫ ХПД')\r\n" + 
        "CONNECT BY\r\n" + 
        "    PRIOR dcks.DCCRDKNDST_ID = dcks.DCCRDKNDST_DCCRDKNDST_ID\r\n" + 
        "    AND dcks.IS_ACTUAL = 1";
}
