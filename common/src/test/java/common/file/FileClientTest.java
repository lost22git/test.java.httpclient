package common.file;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FileClientTest {

    @Test
    void parse_download_uri() {
        var html = """
            <!DOCTYPE HTML>
            <html lang="en_US">

            <head>

                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

                        <meta name="robots" content="index, follow">
                <link rel="canonical" href="https://anonfiles.com/Cfl4xbXcyb/test_png"/>\s
                    <title>test.png - AnonFiles</title>
                            <link href="//vjs.zencdn.net/7.3.0/video-js.min.css" rel="stylesheet">
               \s
                <link rel="stylesheet" href="/css/anonfiles.css?1676030290"/>
                <!--[if lt IE 9]>
                <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
                <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
                <![endif]-->
                <script type="text/javascript">
                    var cfg = {"authenticated":false,"upload_api_endpoint":"https:\\/\\/api.anonfiles.com\\/upload","hua":false,"as":"11","domain":"anonfiles.com","pde":false};
                </script>

                <script src="/js/app.js?1676030290"></script>

                <link rel="shortcut icon" href="/img/favicon/favicon-32x32-anonfiles.png?1668603321"/>
            </head>
            <body>
            <div id="site-wrapper" class="container">

                        <div id="header" class="row">
                <div class="col-xs-0 col-md-3"></div>
                <div class="col-xs-12 col-md-6">
                            <a href="/">
                        <img id="header-logo" class="img-responsive center-block"
                             src="/static/logo.png">
                    </a>
                </div>
                <div class="col-xs-0 col-md-3"></div>
            </div>   \s
                <div class="row">
                <div class="col-xs-0 col-md-3"></div>
                <div class="col-xs-12 col-md-6">
                                <p class="small alert alert-warning text-center alert-maintenance notice-top">
                            <a href="https://anonfiles.com/contribute">
                            <strong>We need your help!</strong>
                            <br />
                                Read more here.<br /></a>
                        <a href="https://asset.gift/campaigns/bdbbbe2b-ab75-4527-b56e-c1f32f7b6396/donate" rel="nofollow" target="_blank">UPDATE: You can now securely donate with your credit card!</a>
                        </p>
                                                                </div>
                <div class="col-xs-0 col-md-3"></div>
            </div>
               \s
                       \s

               \s
                <div class="row top-wrapper">
                    <div class="col-xs-0 col-md-3"></div>
                    <div class="col-xs-12 col-md-6">
                        <h1 class="text-center text-wordwrap">test.png</h1>
                    </div>
                    <div class="col-xs-0 col-md-3"></div>
                </div>

                <script type="text/javascript">var _98f1e40h5='ODFlMS0xNjc2',_0403c8b4a390940aY6='ZXN0LnBuZw==',_f2081e48749n8='MTQzMjM2L3Q=',_23ab7d4b2667201I6='L0NmbDQ=',_13a7fcf1c3='eWIvOGQ4Yw==',_a04b8a6ab3k3='eGJYYw=='; var cdnPath = atob(_23ab7d4b2667201I6)+atob(_a04b8a6ab3k3)+atob(_13a7fcf1c3)+atob(_98f1e40h5)+atob(_f2081e48749n8)+atob(_0403c8b4a390940aY6); var cdnBasePath = 'cdn-149.anonfiles.com';</script>
                <!--
                    Want to check if a file is still available for download?
                    Use the API ( i.e https://api.anonfiles.com/v2/file/Cfl4xbXcyb/info )
                    It is an order of magnitudes faster and reliable.
                    -->
                                    <div id="download-image-preview-wrapper" class="row">
                            <div class="col-xs-0 col-md-3"></div>
                            <div class="col-xs-12 col-md-6">
                                <a id="download-preview-image-url" href="https://cdn-147.anonfiles.com/Cfl4xbXcyb/8d8c81e1-1676143236/test.png"
                                target="_blank">
                                <img id="download-preview-image" class="img-responsive center-block"
                                     src="https://cdn-147.anonfiles.com/Cfl4xbXcyb/8d8c81e1-1676143236/test.png"/>
                                </a>
                            </div>
                            <div class="col-xs-0 col-md-3"></div>
                        </div>
                   \s
                    <div id="download-wrapper" class="row">
                        <div class="col-xs-0 col-md-4"></div>
                        <div class="col-xs-12 col-md-4 text-center">
                                                <a target="_blank" type="button" id="download-url"
                                   class="btn btn-primary btn-block"
                                   href="https://cdn-141.anonfiles.com/Cfl4xbXcyb/8d8c81e1-1676143236/test.png">                    <img
                                        src="/img/file/filetypes/ext/png.png?1668603321"/> Download
                                (333.04 KB)</a>
                                                            <div class="col-xs-0 col-md-4"></div>
                        </div>
                    </div>
               \s

               \s
               \s
                <div class="row">
                <div class="col-xs-0 col-md-3"></div>
                <div id="footer" class="col-xs-12 col-md-6 text-center">
                                <h2>Donations:</h2>
                        Bitcoin: <code class="btc-address-box center-block text-center" style="word-break: break-all; margin-bottom:10px;">bc1qch5p8rg9t88ky5kwect57u0ejws39a4hpz5rkm</code>
                        Monero: <code class="btc-address-box center-block text-center" style="word-break: break-all;">88AW7SHaATAft6nnbrGpFNf7Rq9pWf6umDbUpF9VA9y4abMxyhguroubRcZWyqM6EPGuSamuzWh25GtHY14YGxMBEjRXgzH</code>
                   \s
                                <div class="row bottom-section">
                            <div class="col-xs-12">
                                                        <a hreflang="en" href="https://anonfiles.com/us"><img
                                                class="flag-active"
                                                src="/img/flags/24/us.png"
                                                alt="English"
                                                title="English"></a>
                                                        <a hreflang="de" href="https://anonfiles.com/de"><img
                                                src="/img/flags/24/de.png"
                                                alt="Deutsch"
                                                title="Deutsch"></a>
                                                        <a hreflang="fr" href="https://anonfiles.com/fr"><img
                                                src="/img/flags/24/fr.png"
                                                alt="Français"
                                                title="Français"></a>
                                                        <a hreflang="pt" href="https://anonfiles.com/br"><img
                                                src="/img/flags/24/br.png"
                                                alt="Português (Brazil)"
                                                title="Português (Brazil)"></a>
                                                        <a hreflang="ru" href="https://anonfiles.com/ru"><img
                                                src="/img/flags/24/ru.png"
                                                alt="Русский"
                                                title="Русский"></a>
                                                        <a hreflang="hi" href="https://anonfiles.com/in"><img
                                                src="/img/flags/24/in.png"
                                                alt="हिन्दी"
                                                title="हिन्दी"></a>
                                                        <a hreflang="es" href="https://anonfiles.com/es"><img
                                                src="/img/flags/24/es.png"
                                                alt="Español"
                                                title="Español"></a>
                                                        <a hreflang="nb" href="https://anonfiles.com/no"><img
                                                src="/img/flags/24/no.png"
                                                alt="Norsk"
                                                title="Norsk"></a>
                                                        <a hreflang="sv" href="https://anonfiles.com/se"><img
                                                src="/img/flags/24/se.png"
                                                alt="Svenska"
                                                title="Svenska"></a>
                                                        <a hreflang="da" href="https://anonfiles.com/dk"><img
                                                src="/img/flags/24/dk.png"
                                                alt="Dansk"
                                                title="Dansk"></a>
                                                        <a hreflang="fi" href="https://anonfiles.com/fi"><img
                                                src="/img/flags/24/fi.png"
                                                alt="Suomeksi"
                                                title="Suomeksi"></a>
                                                        <a hreflang="pl" href="https://anonfiles.com/pl"><img
                                                src="/img/flags/24/pl.png"
                                                alt="Polski"
                                                title="Polski"></a>
                                                        <a hreflang="jp" href="https://anonfiles.com/jp"><img
                                                src="/img/flags/24/jp.png"
                                                alt="日本語"
                                                title="日本語"></a>
                                                        <a hreflang="ko" href="https://anonfiles.com/kr"><img
                                                src="/img/flags/24/kr.png"
                                                alt="한국의"
                                                title="한국의"></a>
                                                </div>
                        </div>
                                        <hr/>
                            <div class="row bottom-section">
                        <div class="col-xs-12">
                                                <a href="https://anonfiles.com/login">Login</a><span> - </span>
                                <a href="https://anonfiles.com/register">Register</a><span> - </span>
                                                                                                <a href="https://anonfiles.com/terms">Terms of Use</a><span> - </span>
                                                                <a href="https://anonfiles.com/docs/api">API</a><span> - </span>
                                                                <a href="https://anonfiles.com/faq">FAQ</a><span> - </span>
                                                                <a href="https://anonfiles.com/feedback">Feedback</a><span> - </span>
                                            <a href="https://anonfiles.com/abuse">REPORT ABUSE</a>
                        </div>
                    </div>

                                <div class="row bottom-section">
                            <div class="col-xs-12 sister-sites">
                                <p>Visit our friends:
                                                                <a href="https://filechan.org"
                                                                         target="_blank">filechan</a>
                                                                - <a href="https://letsupload.cc"
                                                                         target="_blank">LetsUpload</a>
                                                        </p>
                            </div>
                        </div>
                                        </div>
                <div class="col-xs-0 col-md-3"></div>
            </div></div>
            <script>
                $.ajaxSetup({headers: {'X-CSRF-Token': 'SXIhW5a4zmsysGUFlc9Qo8J9ZkJJSlTxqOJoFCL6'}});
                var app_csrf_token = "SXIhW5a4zmsysGUFlc9Qo8J9ZkJJSlTxqOJoFCL6";
            </script>

            <script>
                var translate = {
                    upload: {
                        choose_file: "Choose\\u0020file",
                        upload: "Upload",
                        uploading: "Uploading",
                        button_copy: "Copy",
                        button_copied: "Copied\\u0021",
                        browser_too_old: "Your browser is too old &amp; does not support multiple files per upload.",
                        copy_all: {
                            button: "Copy all URLs as...",
                            example_filename: "filename.jpg",
                            options: {
                                text: "Text",
                                bb: "BB code",
                                html: "HTML code"
                            }
                        },
                        error: {
                            ERROR_FILE_NOT_PROVIDED: "No\\u0020file\\u0020chosen.",
                            ERROR_FILE_EMPTY: "The\\u0020file\\u0020can\\u0020not\\u0020be\\u0020empty.",
                            ERROR_FILE_INVALID: "Invalid\\u0020file.",
                            ERROR_USER_MAX_FILES_PER_HOUR_REACHED: "Max\\u0020uploaded\\u0020files\\u0020per\\u0020hour\\u0020exceeded.",
                            ERROR_USER_MAX_FILES_PER_DAY_REACHED: "Max\\u0020uploaded\\u0020files\\u0020per\\u0020day\\u0020exceeded.",
                            ERROR_USER_MAX_BYTES_PER_HOUR_REACHED: "Max\\u0020uploaded\\u0020size\\u0020per\\u0020hour\\u0020exceeded.",
                            ERROR_USER_MAX_BYTES_PER_DAY_REACHED: "Max\\u0020uploaded\\u0020size\\u0020per\\u0020day\\u0020exceeded.",
                            ERROR_FILE_DISALLOWED_TYPE: "Filetype\\u0020not\\u0020allowed.",
                            ERROR_FILE_SIZE_EXCEEDED: "The\\u0020file\\u0020is\\u0020too\\u0020large.\\u0020Max\\u0020filesize\\u003A\\u002020\\u0020GB",
                            ERROR_FILE_BANNED: "The\\u0020file\\u0020content\\u0020is\\u0020banned.",
                            ERROR_SYSTEM_FAILURE: "System\\u0020Error.\\u0020Please\\u0020try\\u0020again\\u0020later."
                        }
                    }
                };
            </script>

            <script src="//vjs.zencdn.net/7.3.0/video.min.js"></script>
                <script src="/sw_anonfiles.js"></script>
                <script data-cfasync="false" src="//djv99sxoqpv11.cloudfront.net/?xsvjd=737329"></script>
            </body>
            </html>
            """;

        assertEquals(Optional.of(URI.create("https://cdn-141.anonfiles.com/Cfl4xbXcyb/8d8c81e1-1676143236/test.png")),
            FileClient.parse_download_uri(html));
    }

    @Test
    void resolve_id_to_uri() {
        var id = "12345";
        assertEquals(URI.create(FileClient.addr + "/" + id), FileClient.resolve(id));
    }
}
