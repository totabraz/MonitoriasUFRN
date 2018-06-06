package totabraz.com.monitoriasufrn.utils;

/**
 * Created by totabraz on 06/03/18.
 */

public abstract class ApiUtils {

    public static final String XAPIKEY = "T1isneQY2Y24hc361KsW9xTMfGhUKb12BnFtxm9V";
    public static final String URLBASE = "https://api.ufrn.br/";
    public static final String URLBASE_TEST = "https://api.info.ufrn.br/";
    public static final String URLBASE_AUTH_TEST = "https://autenticacao.info.ufrn.br/";
    public static final String ID = "deep-id";
    public static final String SECRET = "segredo";
    public static final String URLOAUTH = URLBASE_AUTH_TEST + "authz-server/oauth/token/";

    public static final String TAG_AUTH = "APPTAG - AUTH";

    public static final String QUERY_ATIVO = "ativo=1";
    public static final String QUERY_AND_ATIVO = "&ativo=1";
    public static final String QUERY_CATEG_PROF = "id-categoria=1";
    public static final String QUERY_LIMIT = "limit=100";
    public static final String QUERY_AND_LIMIT = "&limit=100";
    public static final String QUERY_AND_CODIGO_COMPONENTE = "&codigo-componente=";
    public static final String QUERY_AND_IDENTIFICADOR = "&identificador=";
    public static final String QUERY_AND_CPF = "&cpf-cnpj=";

    public static final String CONSULTA_SERVIDORES = URLBASE_TEST + "pessoa/v0.1/servidores";
    public static final String CONSULTA_TURMAS = URLBASE_TEST + "turma/v0.1/turmas?" + QUERY_LIMIT;
    public static final String CONSULTA_VINCULOS = URLBASE_TEST + "vinculo/v0.1/vinculos?" + QUERY_LIMIT + QUERY_AND_ATIVO;
    public static final String CONSULTA_USER_INFO = URLBASE_TEST + "usuario/v0.1/usuarios/info";
    public static final String CONSULTA_USER = URLBASE_TEST + "usuario/v0.1/usuarios?"  + QUERY_LIMIT + QUERY_AND_ATIVO;


    public static final String CONSULTA_TURMAS_USER = URLBASE_TEST + "turma/v0.1/turmas?id-docente=";

}
