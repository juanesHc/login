package com.example.login.service.messaging.impl;

import com.example.login.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public final class EmailHtml {
    String buildVerificationEmailHtml(String userName, String verificationLink) {
        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
        </head>
        <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f4f4f4; padding: 20px;">
                <tr>
                    <td align="center">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                            <!-- Header -->
                            <tr>
                                <td style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); padding: 40px 20px; text-align: center;">
                                    <h1 style="color: #ffffff; margin: 0; font-size: 28px;">Email Verification</h1>
                                </td>
                            </tr>
                            
                            <!-- Content -->
                            <tr>
                                <td style="padding: 40px 30px;">
                                    <h2 style="color: #333333; margin: 0 0 20px 0; font-size: 24px;">
                                        Hello %s! 👋
                                    </h2>
                                    
                                    <p style="color: #666666; font-size: 16px; line-height: 1.6; margin: 0 0 20px 0;">
                                        Thank you for signing up. To complete your registration, please verify your email address by clicking the button below.
                                    </p>
                                    
                                    <!-- Call to Action Button -->
                                    <table width="100%%" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td align="center" style="padding: 20px 0;">
                                                <a href="%s" style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: #ffffff; padding: 14px 40px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">
                                                    Verify Email Address
                                                </a>
                                            </td>
                                        </tr>
                                    </table>
                                    
                                    <p style="color: #666666; font-size: 14px; line-height: 1.6; margin: 20px 0;">
                                        If the button doesn't work, copy and paste this link into your browser:
                                    </p>
                                    
                                    <div style="background-color: #f5f5f5; padding: 15px; border-radius: 5px; word-break: break-all; font-family: monospace; font-size: 12px; color: #333;">
                                        %s
                                    </div>
                                    
                                    <p style="color: #e74c3c; font-size: 14px; line-height: 1.6; margin: 20px 0 0 0; font-weight: bold;">
                                        This link will expire in 7 minutes.
                                    </p>
                                    
                                    <p style="color: #999999; font-size: 14px; line-height: 1.6; margin: 30px 0 0 0;">
                                        If you didn't create this account, you can safely ignore this email.
                                    </p>
                                </td>
                            </tr>
                            
                            <!-- Footer -->
                            <tr>
                                <td style="background-color: #f8f9fa; padding: 20px 30px; text-align: center; border-top: 1px solid #eeeeee;">
                                    <p style="color: #999999; font-size: 12px; margin: 0 0 5px 0;">
                                        This is an automated message, please do not reply.
                                    </p>
                                    <p style="color: #999999; font-size: 12px; margin: 0;">
                                        © 2025 All rights reserved.
                                    </p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(userName, verificationLink, verificationLink);
    }
    String buildWelcomeEmailHtml(PersonEntity personEntity) {
        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
        </head>
        <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f4f4f4; padding: 20px;">
                <tr>
                    <td align="center">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                            <!-- Header -->
                            <tr>
                                <td style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); padding: 40px 20px; text-align: center;">
                                    <h1 style="color: #ffffff; margin: 0; font-size: 28px;">Welcome Aboard!</h1>
                                </td>
                            </tr>
                            
                            <!-- Content -->
                            <tr>
                                <td style="padding: 40px 30px;">
                                    <h2 style="color: #333333; margin: 0 0 20px 0; font-size: 24px;">
                                        Hello %s! 
                                    </h2>
                                    
                                    <p style="color: #666666; font-size: 16px; line-height: 1.6; margin: 0 0 20px 0;">
                                        Great news! Your account has been <strong style="color: #667eea;">successfully verified</strong>.
                                    </p>
                                    
                                    <p style="color: #666666; font-size: 16px; line-height: 1.6; margin: 0 0 30px 0;">
                                        You're all set to start exploring our platform and enjoy all the features we have to offer.
                                    </p>
                                    
                                    <!-- Call to Action Button -->
                                    <table width="100%%" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td align="center" style="padding: 20px 0;">
                                                <a href="#" style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: #ffffff; padding: 14px 40px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">
                                                    Get Started
                                                </a>
                                            </td>
                                        </tr>
                                    </table>
                                    
                                    <p style="color: #999999; font-size: 14px; line-height: 1.6; margin: 30px 0 0 0;">
                                        If you have any questions, feel free to reach out to our support team.
                                    </p>
                                </td>
                            </tr>
                            
                            <!-- Footer -->
                            <tr>
                                <td style="background-color: #f8f9fa; padding: 20px 30px; text-align: center; border-top: 1px solid #eeeeee;">
                                    <p style="color: #999999; font-size: 12px; margin: 0;">
                                        © 2025 Your Company. All rights reserved.
                                    </p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(personEntity.getGivenName());
    }
}
