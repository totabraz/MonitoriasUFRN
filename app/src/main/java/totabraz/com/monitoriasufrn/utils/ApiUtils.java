package totabraz.com.monitoriasufrn.utils;

/**
 * Created by totabraz on 06/03/18.
 */

public abstract class ApiUtils {

    public static final String XAPIKEY = "T1isneQY2Y24hc361KsW9xTMfGhUKb12BnFtxm9V";
    public static final String URLBASE = "https://api.info.ufrn.br/";
    public static final String URLBASE_AUTH = "https://autenticacao.info.ufrn.br/";
    public static final String ID = "deep-id";
    public static final String SECRET = "segredo";
    public static final String URLOAUTH = URLBASE_AUTH + "authz-server/oauth/token/";

    public static final String TAG_AUTH = "APPTAG - AUTH";

    public static final String CONSULTASERVIDORES = "pessoa/v0.1/servidores";
    public static final String QUERY_ATIVO = "id-ativo=1";
    public static final String QUERY_CATEG_PROF = "id-categoria=1";
    public static final String QUERY_LIMIT = "limit=100";

}
