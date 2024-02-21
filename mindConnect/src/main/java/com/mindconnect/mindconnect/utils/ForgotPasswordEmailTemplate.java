package com.mindconnect.mindconnect.utils;

public class ForgotPasswordEmailTemplate {
  public static String password(String token) {
    String link = "http://localhost:8020/auth/reset-password?token=" + token;
    return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
            "<head>\n" +
            "<!--[if gte mso 9]>\n" +
            "<xml>\n" +
            "  <o:OfficeDocumentSettings>\n" +
            "    <o:AllowPNG/>\n" +
            "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
            "  </o:OfficeDocumentSettings>\n" +
            "</xml>\n" +
            "<![endif]-->\n" +
            "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
            "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
            "  <title></title>\n" +
            "\n" +
            "    <style type=\"text/css\">\n" +
            "  @media only screen and (min-width: 620px) {\n" +
            "  .u-row {\n" +
            "      width: 600px !important;\n" +
            "    }\n" +
            "  .u-row .u-col {\n" +
            "      vertical-align: top;\n" +
            "    }\n" +
            "\n" +
            "  .u-row .u-col-100 {\n" +
            "      width: 600px !important;\n" +
            "    }\n" +
            "\n" +
            "  }\n" +
            "\n" +
            "  @media (max-width: 620px) {\n" +
            "  .u-row-container {\n" +
            "      max-width: 100% !important;\n" +
            "      padding-left: 0px !important;\n" +
            "      padding-right: 0px !important;\n" +
            "    }\n" +
            "  .u-row .u-col {\n" +
            "      min-width: 320px !important;\n" +
            "      max-width: 100% !important;\n" +
            "      display: block !important;\n" +
            "    }\n" +
            "  .u-row {\n" +
            "      width: 100% !important;\n" +
            "    }\n" +
            "  .u-col {\n" +
            "      width: 100% !important;\n" +
            "    }\n" +
            "  .u-col > div {\n" +
            "      margin: 0 auto;\n" +
            "    }\n" +
            "  }\n" +
            "  body {\n" +
            "    margin: 0;\n" +
            "    padding: 0;\n" +
            "  }\n" +
            "\n" +
            "  table,\n" +
            "  tr,\n" +
            "  td {\n" +
            "    vertical-align: top;\n" +
            "    border-collapse: collapse;\n" +
            "  }\n" +
            "\n" +
            ".ie-container table,\n" +
            ".mso-container table {\n" +
            "    table-layout: fixed;\n" +
            "  }\n" +
            "\n" +
            "* {\n" +
            "    line-height: inherit;\n" +
            "  }\n" +
            "\n" +
            "  a[x-apple-data-detectors='true'] {\n" +
            "    color: inherit !important;\n" +
            "    text-decoration: none !important;\n" +
            "  }\n" +
            "\n" +
            "  table, td { color: #000000; } #u_body a { color: #0000ee; text-decoration: underline; } @media (max-width: 480px) { #u_content_heading_1 .v-container-padding-padding { padding: 10px 10px 20px !important; } #u_content_heading_1 .v-font-size { font-size: 22px !important; } #u_content_heading_2 .v-container-padding-padding { padding: 40px 10px 10px !important; } #u_content_button_1 .v-container-padding-padding { padding: 30px 10px 40px !important; } #u_content_button_1 .v-size-width { width: 65% !important; } }\n" +
            "    </style>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Raleway:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9ff;color: #000000\">\n" +
            "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
            "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
            "  <table id=\"u_body\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9ff;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "  <tbody>\n" +
            "  <tr style=\"vertical-align: top\">\n" +
            "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
            "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9ff;\"><![endif]-->\n" +
            "\n" +
            "\n" +
            "\n" +
            "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
            "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
            "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
            "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
            "\n" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
            "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
            "  <div style=\"background-color: #ffffff;height: 100%;width: 100% !important;\">\n" +
            "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
            "\n" +
            "<table id=\"u_content_heading_1\" style=\"font-family:'Raleway',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
            "  <tbody>\n" +
            "    <tr>\n" +
            "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 30px;font-family:'Raleway',sans-serif;\" align=\"left\">\n" +
            "\n" +
            "  <!--[if mso]><table width=\"100%\"><tr><td><![endif]-->\n" +
            "    <h1 class=\"v-font-size\" style=\"margin: 0px; line-height: 140%; text-align: center; word-wrap: break-word; font-size: 28px; font-weight: 400;\"><span><strong>Forget password ?</strong></span></h1>\n" +
            "  <!--[if mso]></td></tr></table><![endif]-->\n" +
            "\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "  </tbody>\n" +
            "</table>\n" +
            "\n" +
            "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
            "  </div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
            "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
            "    </div>\n" +
            "  </div>\n" +
            "  </div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
            "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
            "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
            "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
            "\n" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color: #ffffff;width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
            "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
            "  <div style=\"background-color: #ffffff;height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
            "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\"><!--<![endif]-->\n" +
            "\n" +
            "<table id=\"u_content_heading_2\" style=\"font-family:'Raleway',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
            "  <tbody>\n" +
            "    <tr>\n" +
            "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 60px 10px;font-family:'Raleway',sans-serif;\" align=\"left\">\n" +
            "\n" +
            "  <!--[if mso]><table width=\"100%\"><tr><td><![endif]-->\n" +
            "    <h1 class=\"v-font-size\" style=\"margin: 0px; line-height: 140%; text-align: left; word-wrap: break-word; font-size: 16px; font-weight: 400;\"><span>If you've lost your password or wish to reset it, use the link below to get started:</span></h1>\n" +
            "  <!--[if mso]></td></tr></table><![endif]-->\n" +
            "\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "  </tbody>\n" +
            "</table>\n" +
            "\n" +
            "<table id=\"u_content_button_1\" style=\"font-family:'Raleway',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
            "  <tbody>\n" +
            "    <tr>\n" +
            "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:30px 10px 40px;font-family:'Raleway',sans-serif;\" align=\"left\">\n" +
            "\n" +
            "  <!--[if mso]><style>.v-button {background: transparent !important;}</style><![endif]-->\n" +
            "<div align=\"center\">\n" +
            "  <!--[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://www.unlayer.com\" style=\"height:37px; v-text-anchor:middle; width:220px;\" arcsize=\"67.5%\"  stroke=\"f\" fillcolor=\"#3aaee0\"><w:anchorlock/><center style=\"color:#ffffff;\"><![endif]-->\n" +
            "    <a href=\""+ link +"\" target=\"_blank\" class=\"v-button v-size-width v-font-size\" style=\"box-sizing: border-box;display: inline-block;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #ffffff; background-color: #3aaee0; border-radius: 25px;-webkit-border-radius: 25px; -moz-border-radius: 25px; width:38%; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;font-size: 14px;\">\n" +
            "      <span style=\"display:block;padding:10px 20px;line-height:120%;\"><span style=\"line-height: 16.8px;\">Reset Your Password</span></span>\n" +
            "    </a>\n" +
            "    <!--[if mso]></center></v:roundrect><![endif]-->\n" +
            "</div>\n" +
            "\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "  </tbody>\n" +
            "</table>\n" +
            "\n" +
            "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
            "  </div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
            "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
            "    </div>\n" +
            "  </div>\n" +
            "  </div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
            "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
            "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
            "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
            "\n" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\" valign=\"top\"><![endif]-->\n" +
            "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
            "  <div style=\"height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\">\n" +
            "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;\"><!--<![endif]-->\n" +
            "\n" +
            "<table style=\"font-family:'Raleway',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
            "  <tbody>\n" +
            "    <tr>\n" +
            "      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:0px;font-family:'Raleway',sans-serif;\" align=\"left\">\n" +
            "\n" +
            "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #BBBBBB;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
            "    <tbody>\n" +
            "      <tr style=\"vertical-align: top\">\n" +
            "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
            "          <span>&#160;</span>\n" +
            "        </td>\n" +
            "      </tr>\n" +
            "    </tbody>\n" +
            "  </table>\n" +
            "\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "  </tbody>\n" +
            "</table>\n" +
            "\n" +
            "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
            "  </div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
            "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
            "    </div>\n" +
            "  </div>\n" +
            "  </div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
            "    </td>\n" +
            "  </tr>\n" +
            "  </tbody>\n" +
            "  </table>\n" +
            "  <!--[if mso]></div><![endif]-->\n" +
            "  <!--[if IE]></div><![endif]-->\n" +
            "</body>\n" +
            "\n" +
            "</html>";
  }
}
