package com.script972.clutchclient.api;



public class ApiClient {

    public enum Environment {DEV, STAGE, PROD}

    public static class OAuth {
        public static final String BASE_URL = getBaseUrl(Environment.DEV);

        public static class Credentials {
            public static final String CLIENT_ID = getClientID(Environment.DEV);
            public static final String CLIENT_SECRET = getClientSecret(Environment.DEV);
            public static final String GRANT_TYPE_PASSWORD = "password";
            public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
        }

        public static class Header {
            public static final String AUTH_TOKEN_KEY = "Authorization";

            public static String buildAuthHeaderValue(String accessToken) {
                return "Bearer " + accessToken;
            }
        }

        private static class ProdEnvironment {
            static final String BASE_URL = "https://mobile.boostcx.com/IdentityServer/";
            static final String CLIENT_ID = "BOOSTcxMobile";
            static final String CLIENT_SECRET = "ylZrG1R8h0EP9eIOzLIEKX9917Qa1jf4RxCAeu5g3zs=";
        }

        private static class StageEnvironment {
            static final String BASE_URL = "http://hmadev.boostcx.com/IdentityServer_UAT/";
            static final String CLIENT_ID = "BOOSTcxMobile";
            static final String CLIENT_SECRET = "ylZrG1R8h0EP9eIOzLIEKX9917Qa1jf4RxCAeu5g3zs=";
        }

        private static class DevEnvironment {
            static final String BASE_URL = "http://hmadev.boostcx.com/IdentityServer/";
            static final String CLIENT_ID = "BOOSTcxMobile";
            static final String CLIENT_SECRET = "ylZrG1R8h0EP9eIOzLIEKX9917Qa1jf4RxCAeu5g3zs=";
        }

        private static String getBaseUrl(Environment buildEnvironment) {
            switch (buildEnvironment) {
                case DEV:
                    return DevEnvironment.BASE_URL;
                case STAGE:
                    return StageEnvironment.BASE_URL;
                case PROD:
                    return ProdEnvironment.BASE_URL;
                default:
                    return ProdEnvironment.BASE_URL;
            }
        }

        private static String getClientID(Environment buildEnvironment) {
            switch (buildEnvironment) {
                case DEV:
                    return DevEnvironment.CLIENT_ID;
                case STAGE:
                    return StageEnvironment.CLIENT_ID;
                case PROD:
                    return ProdEnvironment.CLIENT_ID;
                default:
                    return ProdEnvironment.CLIENT_ID;
            }
        }

        private static String getClientSecret(Environment buildEnvironment) {
            switch (buildEnvironment) {
                case DEV:
                    return DevEnvironment.CLIENT_SECRET;
                case STAGE:
                    return StageEnvironment.CLIENT_SECRET;
                case PROD:
                    return ProdEnvironment.CLIENT_SECRET;
                default:
                    return ProdEnvironment.CLIENT_SECRET;
            }
        }

    }


    public static class ClutchClient {
        private static final String DEV = "18.219.228.241:8080";
        //TODO change ip constante
        private static final String STAGE = "18.219.228.241:8080";
        private static final String PROD = "18.219.228.241:8080";
        private static final String BASE_URL = getBaseUrl(Environment.DEV);
        private static final String BASE_ENDPOINT = "api";

        public static final String BASE_API_URL = "http://"+BASE_URL + "/" + BASE_ENDPOINT + "/";

        private static String getBaseUrl(Environment buildEnvironment) {
            switch (buildEnvironment) {
                case DEV:
                    return DEV;
                case STAGE:
                    return STAGE;
                case PROD:
                    return PROD;
                default:
                    return PROD;
            }
        }
    }









}
