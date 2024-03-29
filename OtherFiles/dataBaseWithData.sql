USE [sample_webservice_db_dev]
GO
/****** Object:  User [dbuser]    Script Date: 03/10/2020 09:56:31 PM ******/
CREATE USER [dbuser] FOR LOGIN [dbuser] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [dbuser]
GO
ALTER ROLE [db_accessadmin] ADD MEMBER [dbuser]
GO
ALTER ROLE [db_securityadmin] ADD MEMBER [dbuser]
GO
ALTER ROLE [db_ddladmin] ADD MEMBER [dbuser]
GO
ALTER ROLE [db_backupoperator] ADD MEMBER [dbuser]
GO
ALTER ROLE [db_datareader] ADD MEMBER [dbuser]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [dbuser]
GO
/****** Object:  Table [dbo].[tbl_app_config_settings]    Script Date: 03/10/2020 09:56:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_app_config_settings](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[config_key] [varchar](50) NOT NULL,
	[config_value] [varchar](250) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime] NOT NULL,
	[status] [smallint] NOT NULL,
	[updated_by] [bigint] NULL,
	[updated_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_roles]    Script Date: 03/10/2020 09:56:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_roles](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime] NOT NULL,
	[role_name] [varchar](50) NOT NULL,
	[status] [smallint] NOT NULL,
	[updated_by] [bigint] NULL,
	[updated_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_user_accounts]    Script Date: 03/10/2020 09:56:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_user_accounts](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime] NOT NULL,
	[password] [varchar](250) NOT NULL,
	[status] [smallint] NOT NULL,
	[updated_by] [bigint] NULL,
	[updated_date] [datetime] NULL,
	[username] [varchar](100) NOT NULL,
	[role_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_users]    Script Date: 03/10/2020 09:56:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_users](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime] NOT NULL,
	[email_address] [varchar](250) NULL,
	[full_name] [varchar](250) NOT NULL,
	[phone_number] [bigint] NULL,
	[status] [smallint] NOT NULL,
	[updated_by] [bigint] NULL,
	[updated_date] [datetime] NULL,
	[user_account_id] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tbl_app_config_settings] ON 

INSERT [dbo].[tbl_app_config_settings] ([id], [config_key], [config_value], [created_by], [created_date], [status], [updated_by], [updated_date]) VALUES (2, N'JWT_SECRET_KEY', N'eyJhbGuyausuguuguyt76t36272dwkjbckjjlhaHSDYO87Y2O89FHC2Y8DEKUILAHDYWR2Y38Y8EDHEWHDJDH983298fhewdksjc', 1, CAST(N'2019-09-07T13:50:34.000' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[tbl_app_config_settings] ([id], [config_key], [config_value], [created_by], [created_date], [status], [updated_by], [updated_date]) VALUES (3, N'JWT_ACCESS_TOKEN_EXPIRATION_TIME', N'3600000', 1, CAST(N'2019-09-07T13:50:34.000' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[tbl_app_config_settings] ([id], [config_key], [config_value], [created_by], [created_date], [status], [updated_by], [updated_date]) VALUES (4, N'JWT_REFRESH_TOKEN_EXPIRATION_TIME', N'86400000', 1, CAST(N'2019-09-07T13:50:34.000' AS DateTime), 1, NULL, NULL)
SET IDENTITY_INSERT [dbo].[tbl_app_config_settings] OFF
SET IDENTITY_INSERT [dbo].[tbl_roles] ON 

INSERT [dbo].[tbl_roles] ([id], [created_by], [created_date], [role_name], [status], [updated_by], [updated_date]) VALUES (1, 1, CAST(N'2019-04-04T10:57:27.517' AS DateTime), N'ADMIN', 1, NULL, NULL)
INSERT [dbo].[tbl_roles] ([id], [created_by], [created_date], [role_name], [status], [updated_by], [updated_date]) VALUES (2, 1, CAST(N'2019-04-04T10:57:27.517' AS DateTime), N'USER', 1, NULL, NULL)
SET IDENTITY_INSERT [dbo].[tbl_roles] OFF
SET IDENTITY_INSERT [dbo].[tbl_user_accounts] ON 

INSERT [dbo].[tbl_user_accounts] ([id], [created_by], [created_date], [password], [status], [updated_by], [updated_date], [username], [role_id]) VALUES (1, 1, CAST(N'2019-05-12T10:21:40.020' AS DateTime), N'$2a$06$F9Yzx1iDc.kb3gtTlTG9J.b7I/2XlbhVf.HT1pi9C8gXHLHZyf0EW', 1, 1, CAST(N'2019-05-12T10:21:40.020' AS DateTime), N'admin', 1)
INSERT [dbo].[tbl_user_accounts] ([id], [created_by], [created_date], [password], [status], [updated_by], [updated_date], [username], [role_id]) VALUES (2, 1, CAST(N'2020-10-03T21:51:39.107' AS DateTime), N'$2a$06$0k/f3z.gBd3Kw.LYX7TefevbGPN1aoZR9PBvZTvvtMQ4hWbTtyEoO', 1, NULL, NULL, N'TestUser', 2)
SET IDENTITY_INSERT [dbo].[tbl_user_accounts] OFF
SET IDENTITY_INSERT [dbo].[tbl_users] ON 

INSERT [dbo].[tbl_users] ([id], [created_by], [created_date], [email_address], [full_name], [phone_number], [status], [updated_by], [updated_date], [user_account_id]) VALUES (1, 1, CAST(N'2019-05-12T10:21:40.020' AS DateTime), N'admin@test.com', N'Administrator', 1000000000, 1, 1, CAST(N'2019-05-12T10:21:40.020' AS DateTime), 1)
INSERT [dbo].[tbl_users] ([id], [created_by], [created_date], [email_address], [full_name], [phone_number], [status], [updated_by], [updated_date], [user_account_id]) VALUES (2, 1, CAST(N'2020-10-03T21:51:39.430' AS DateTime), N'test@yahoo.com', N'Test User', 100091102, 1, NULL, NULL, 2)
SET IDENTITY_INSERT [dbo].[tbl_users] OFF
ALTER TABLE [dbo].[tbl_user_accounts]  WITH CHECK ADD  CONSTRAINT [FK4nx93bhhqiqpmtygugyl6n5w4] FOREIGN KEY([role_id])
REFERENCES [dbo].[tbl_roles] ([id])
GO
ALTER TABLE [dbo].[tbl_user_accounts] CHECK CONSTRAINT [FK4nx93bhhqiqpmtygugyl6n5w4]
GO
ALTER TABLE [dbo].[tbl_users]  WITH CHECK ADD  CONSTRAINT [FKd8cv10s6117r5e8sd1ii8qr39] FOREIGN KEY([user_account_id])
REFERENCES [dbo].[tbl_user_accounts] ([id])
GO
ALTER TABLE [dbo].[tbl_users] CHECK CONSTRAINT [FKd8cv10s6117r5e8sd1ii8qr39]
GO
