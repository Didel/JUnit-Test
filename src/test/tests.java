package test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domeinLaag.*;

public class tests
{
	private static Fabrikant f1;
	private static LuchtvaartMaatschappij lvm;
	private static Fabrikant f2;
	private static VliegtuigType vtt1;
	private static VliegtuigType vtt2;
	private static VliegtuigType vtt3;
	private static VliegtuigType vtt4;
	private static Calendar datum;
	private static Vliegtuig vt1;
	private static Vliegtuig vt2;
	private static Vliegtuig vt3;
	private static Land l1;
	private static Land l2;
	private static Land l3;
	private static Land l4;
	private static Luchthaven lh1;
	private static Luchthaven lh2;
	private static Luchthaven lh3;
	private static Calendar vertrek;
	private static Calendar aankomst;
	private Vlucht vl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		lvm = new LuchtvaartMaatschappij("NLM");
		f1 = new Fabrikant("Airbus","G. Dejenelle");
	    f2 = new Fabrikant("Fokker","A.J. Berger");

	    vtt1 = f1.creeerVliegtuigType("A-200", 60, 80);
		vtt2 = f1.creeerVliegtuigType("A-201", 65, 88);
		vtt3 = f2.creeerVliegtuigType("F-100", 37, 70);
		vtt4 = f2.creeerVliegtuigType("F-101", 45, 82);

		datum = Calendar.getInstance();
		datum.set(2000, 01, 01);
		vt1 = new Vliegtuig(lvm, vtt1, "Luchtbus 100", datum);
		datum.set(2006, 06, 01);
		vt2 = new Vliegtuig(lvm, vtt3, "Karel Doorman", datum);
		datum.set(1999, 11, 01);
		vt3 = new Vliegtuig(lvm, vtt4, "Fokke", datum);
		
		l1 = new Land("Nederland", 31);
		l2 = new Land("Belgi��", 32);
		l3 = new Land("Frankrijk", 11);
		l4 = new Land("Duitsland", 13);
		
		lh1 = new Luchthaven("Schiphol", "ASD", true, l1);
		lh2 = new Luchthaven("Charles de Gaule", "PRI", true, l3);
		lh3 = new Luchthaven("Tegel", "TEG", true, l4);
		
		vertrek = Calendar.getInstance();
		vertrek.set(2008, 0, 1, 12, 0);
		aankomst = Calendar.getInstance();
		aankomst.set(2008, 0, 2, 12, 0);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		vl = new Vlucht (vt3, lh1);
		
	}

	@After
	public void tearDown() throws Exception
	{
		vertrek.clear();
		aankomst.clear();
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testVertrekBestemmingGelijk () throws VluchtException
	{	
		vl.zetBestemming(lh1);
	}
	
	@Test
	public void testVertrekBestemmingOngelijk () throws VluchtException
	{
		vl.zetBestemming(lh3);
	}
	
	@Test (expected=VluchtException.class)
	public void testOngeldigeVertrekTijd () throws VluchtException
	{
		vertrek.clear();
		vertrek.set(2008, 2, 31, 12, 0);
		vl.zetVertrekTijd(vertrek);
	}
	
	@Test
	public void testGeldigeVertrekTijd () throws VluchtException
	{
		vertrek.set(2008, 1, 1, 11, 59);
		vl.zetVertrekTijd(vertrek);
	}
	
	@Test (expected=VluchtException.class)
	public void testVertrekOverlapTijden () throws VluchtException
	{
		vertrek.set(2008, 1, 2, 12, 01);
		aankomst.set(2008, 12, 12, 12, 0);
		Vlucht vl2 = new Vlucht (vt3, lh1, lh3, vertrek, aankomst);
		vertrek.set(2008, 1, 2, 12, 00);
		Vlucht vl3 = new Vlucht (vt3, lh1);
		vl.zetAankomstTijd(aankomst);
		vl3.zetBestemming(lh3);
		vl3.zetVertrekTijd(vertrek);
	}
	
	@Test (expected=VluchtException.class)
	public void testAankomstTijdVoorVertrekTijd () throws VluchtException
	{
		vertrek.set (2008, 1, 2, 12, 00);
		aankomst.set (2008, 1, 2, 10, 00);
		vl.zetVertrekTijd(vertrek);
		vl.zetAankomstTijd(aankomst);
	}
	
	@Test (expected=VluchtException.class)
	public void testVertrektAankomsttGelijk () throws VluchtException
	{
		vertrek.set(2008, 1, 2, 12, 00);
		vl.zetVertrekTijd(vertrek);
		aankomst.set(2008, 1, 2, 12, 00);
		vl.zetAankomstTijd(aankomst);
	}
	
	@Test
	public void testVertrekAankomstTijdOngelijk () throws VluchtException
	{
		vertrek.set(2008, 3, 1, 12, 0);
		aankomst.set(2008, 3, 1, 14, 0);
		
		vl.zetVertrekTijd(vertrek);
		vl.zetAankomstTijd(aankomst);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testZonderVliegtuig () throws VluchtException
	{
		vertrek.set(2008, 3, 1, 12, 0);
		aankomst.set(2008, 3, 1, 14, 0);
		vl = new Vlucht (null, lh1);
		vl.zetBestemming(lh3);
		vl.zetVertrekTijd(vertrek);
		vl.zetAankomstTijd(aankomst);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testZonderVertrekPunt () throws VluchtException
	{
		vertrek.set(2008, 3, 1, 12, 0);
		aankomst.set(2008, 3, 1, 14, 0);
		vl = new Vlucht (vt3, null);
		vl.zetBestemming(lh3);
		vl.zetVertrekTijd(vertrek);
		vl.zetAankomstTijd(aankomst);
	}
	
	@Test (expected=VluchtException.class)
	public void testZonderBestemming () throws VluchtException
	{
		vertrek.set(2008, 3, 1, 12, 0);
		aankomst.set(2008, 3, 1, 14, 0);
		vl.zetVertrekTijd(vertrek);
		vl.zetAankomstTijd(aankomst);
		vl.bewaar();
	}
	
	@Test (expected=VluchtException.class)
	public void testZonderVertrekTijd () throws VluchtException
	{
		vl.zetBestemming(lh3);
		aankomst.set(2008, 3, 1, 14, 0);
		vl.zetAankomstTijd(aankomst);
		vl.bewaar();
	}
	
	@Test (expected=VluchtException.class)
	public void testZonderAankomstTijd () throws VluchtException
	{
		vl.zetBestemming(lh3);
		vertrek.set(2008, 3, 1, 12, 0);
		vl.zetVertrekTijd(vertrek);
		vl.bewaar();
	}
	
	@Test
	public void testGeefAlleVluchten ()
	{
		Vlucht.geefAlleVluchten();
	}
}
