USE [master]
GO
/****** Object:  Database [Mediateca]    Script Date: 3/11/2024 00:21:42 ******/
CREATE DATABASE [Mediateca]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Mediateca', FILENAME = N'G:\vm\sql\MSSQL16.MSSQLSERVER\MSSQL\DATA\Mediateca.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Mediateca_log', FILENAME = N'G:\vm\sql\MSSQL16.MSSQLSERVER\MSSQL\DATA\Mediateca_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Mediateca] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Mediateca].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Mediateca] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Mediateca] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Mediateca] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Mediateca] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Mediateca] SET ARITHABORT OFF 
GO
ALTER DATABASE [Mediateca] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Mediateca] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Mediateca] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Mediateca] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Mediateca] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Mediateca] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Mediateca] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Mediateca] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Mediateca] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Mediateca] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Mediateca] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Mediateca] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Mediateca] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Mediateca] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Mediateca] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Mediateca] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Mediateca] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Mediateca] SET RECOVERY FULL 
GO
ALTER DATABASE [Mediateca] SET  MULTI_USER 
GO
ALTER DATABASE [Mediateca] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Mediateca] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Mediateca] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Mediateca] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Mediateca] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Mediateca] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Mediateca', N'ON'
GO
ALTER DATABASE [Mediateca] SET QUERY_STORE = ON
GO
ALTER DATABASE [Mediateca] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Mediateca]
GO
/****** Object:  Table [dbo].[Autores]    Script Date: 3/11/2024 00:21:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Autores](
	[IDAutor] [varchar](10) NOT NULL,
	[NombreAutor] [varchar](50) NOT NULL,
	[Nacionalidad] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDAutor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CDsAudio]    Script Date: 3/11/2024 00:21:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CDsAudio](
	[CDID] [varchar](10) NOT NULL,
	[TituloCD] [varchar](100) NOT NULL,
	[Artista] [varchar](50) NOT NULL,
	[Genero] [varchar](20) NOT NULL,
	[Duracion] [int] NOT NULL,
	[NumeroCanciones] [int] NOT NULL,
	[UnidadesDisponibles] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CDID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Clientes]    Script Date: 3/11/2024 00:21:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Clientes](
	[IDCliente] [varchar](10) NOT NULL,
	[NombreCliente] [varchar](50) NOT NULL,
	[ApellidoCliente] [varchar](50) NOT NULL,
	[DUICliente] [varchar](10) NOT NULL,
	[DireccionCliente] [varchar](100) NOT NULL,
	[TelefonoCliente] [varchar](15) NOT NULL,
	[EmailCliente] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[DUICliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[EmailCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DVDs]    Script Date: 3/11/2024 00:21:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DVDs](
	[DVDID] [varchar](10) NOT NULL,
	[TituloDVD] [varchar](100) NOT NULL,
	[Director] [varchar](50) NOT NULL,
	[Genero] [varchar](20) NOT NULL,
	[Duracion] [int] NOT NULL,
	[UnidadesDisponibles] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[DVDID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Editoriales]    Script Date: 3/11/2024 00:21:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Editoriales](
	[EditorialID] [varchar](10) NOT NULL,
	[NombreEditorial] [varchar](50) NOT NULL,
	[PaisEditorial] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[EditorialID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Libros]    Script Date: 3/11/2024 00:21:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Libros](
	[LibroID] [varchar](10) NOT NULL,
	[TituloLibro] [varchar](100) NOT NULL,
	[IDAutor] [varchar](10) NOT NULL,
	[EditorialID] [varchar](10) NOT NULL,
	[Paginas] [int] NOT NULL,
	[ISBN] [varchar](20) NOT NULL,
	[AnioPublicacion] [int] NOT NULL,
	[UnidadesDisponibles] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[LibroID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Prestamos]    Script Date: 3/11/2024 00:21:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Prestamos](
	[IDPrestamo] [varchar](20) NOT NULL,
	[IDCliente] [varchar](10) NOT NULL,
	[CDID] [varchar](10) NULL,
	[DVDID] [varchar](10) NULL,
	[LibroID] [varchar](10) NULL,
	[RevistaID] [varchar](10) NULL,
	[FechaPrestamo] [date] NOT NULL,
	[FechaDevolucion] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDPrestamo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Revistas]    Script Date: 3/11/2024 00:21:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Revistas](
	[RevistaID] [varchar](10) NOT NULL,
	[TituloRevista] [varchar](100) NOT NULL,
	[EditorialID] [varchar](10) NOT NULL,
	[Periodicidad] [varchar](20) NOT NULL,
	[FechaPublicacion] [date] NOT NULL,
	[UnidadesDisponibles] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[RevistaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Prestamos] ADD  DEFAULT (NULL) FOR [CDID]
GO
ALTER TABLE [dbo].[Prestamos] ADD  DEFAULT (NULL) FOR [DVDID]
GO
ALTER TABLE [dbo].[Prestamos] ADD  DEFAULT (NULL) FOR [LibroID]
GO
ALTER TABLE [dbo].[Prestamos] ADD  DEFAULT (NULL) FOR [RevistaID]
GO
ALTER TABLE [dbo].[Libros]  WITH CHECK ADD FOREIGN KEY([EditorialID])
REFERENCES [dbo].[Editoriales] ([EditorialID])
GO
ALTER TABLE [dbo].[Libros]  WITH CHECK ADD FOREIGN KEY([IDAutor])
REFERENCES [dbo].[Autores] ([IDAutor])
GO
ALTER TABLE [dbo].[Prestamos]  WITH CHECK ADD FOREIGN KEY([CDID])
REFERENCES [dbo].[CDsAudio] ([CDID])
GO
ALTER TABLE [dbo].[Prestamos]  WITH CHECK ADD FOREIGN KEY([DVDID])
REFERENCES [dbo].[DVDs] ([DVDID])
GO
ALTER TABLE [dbo].[Prestamos]  WITH CHECK ADD FOREIGN KEY([IDCliente])
REFERENCES [dbo].[Clientes] ([IDCliente])
GO
ALTER TABLE [dbo].[Prestamos]  WITH CHECK ADD FOREIGN KEY([LibroID])
REFERENCES [dbo].[Libros] ([LibroID])
GO
ALTER TABLE [dbo].[Prestamos]  WITH CHECK ADD FOREIGN KEY([RevistaID])
REFERENCES [dbo].[Revistas] ([RevistaID])
GO
ALTER TABLE [dbo].[Revistas]  WITH CHECK ADD FOREIGN KEY([EditorialID])
REFERENCES [dbo].[Editoriales] ([EditorialID])
GO
USE [master]
GO
ALTER DATABASE [Mediateca] SET  READ_WRITE 
GO
