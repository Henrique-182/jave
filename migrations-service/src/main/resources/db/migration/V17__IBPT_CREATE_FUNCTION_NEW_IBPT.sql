CREATE OR REPLACE FUNCTION IBPT.FUNC_NEW_IBPT_VOID_PLPGSQL(P_ID_VERSION INT) RETURNS VOID AS $$
BEGIN
	INSERT INTO IBPT.TB_IBPT(FK_VERSION, FK_COMPANY_SOFTWARE, IS_UPDATED)
	     SELECT P_ID_VERSION, COSO.ID, FALSE
	       FROM SISTEMAS.TB_COMPANY_SOFTWARE COSO
     INNER JOIN SISTEMAS.TB_COMPANY COMP ON COSO.FK_COMPANY = COMP.ID
	      WHERE (COMP.IS_ACTIVE = TRUE AND COSO.IS_ACTIVE = TRUE)
	        AND ((SELECT COUNT(*) FROM IBPT.TB_IBPT IBPT WHERE IBPT.FK_VERSION = P_ID_VERSION and IBPT.FK_COMPANY_SOFTWARE = COSO.ID ) = 0)
            AND COSO.TYPE = 'Fiscal';
END
$$ LANGUAGE PLPGSQL;
