package com.psa.entities.resolvers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.psa.constants.PSAConstants;
import com.psa.entities.serializable.EndPointBase;

public class TableResolver implements PSAConstants {
	
	/**
	 * Returns the list of classes implementing the interface in a given package
	 * @param interfaceName {@link String} - input interface name
	 * @return {@link List} < {@link Class} > - List of classes
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List < Class > getClassesImplementingInterface ( String interfaceName , String tablePackage ) throws Exception {
		
		List< Class > tableDefs = new ArrayList< Class >();
		try {
		
			Class[] names = getClasses( tablePackage );

			for( Class a : names ) {
				
				if ( interfaceName != null ) {
					
					if( ! a.getName().contains( DOLLAR ) ) {

						for( Class b : a.getInterfaces() ) {

							if( b.getName().equalsIgnoreCase( interfaceName ) ) {
								
								tableDefs.add( a );
							}
						}
					}
				} else {
					
					if( ! a.getName().contains( DOLLAR ) ) {

						for( Class b : a.getInterfaces() ) {

							if( b.getName().equalsIgnoreCase( SOURCE_INTERFACE ) ) {
								
								tableDefs.add( a );
							}
							
							if( b.getName().equalsIgnoreCase( TARGET_INTERFACE ) ) {
								
								tableDefs.add( a );
							}
						}
					}
				}
			}
		} catch ( IOException e ) {
			
			e.printStackTrace();
		}
		
		return tableDefs;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static List< String > getInterfaces ( String className ) throws Exception {
		
		List< String > interfaces = new ArrayList< String >();
		for( Class a : Class.forName( className ).getInterfaces() ) {
				
			if( ! a.getName().contains( DOLLAR ) ) {

				interfaces.add( a.getName() );
			}
		}
		return interfaces;
	}
	
	
	/**
	 * Returns the List of classes extending an input className
	 * @param className
	 * @return {@link List}< {@link Class} >
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List< Class > getClassesExtendingClass ( String className , String tablePackage ) throws Exception {
		
		List< Class > tableDefs = new ArrayList< Class >();
		try {
		
			Class[] names = getClasses( tablePackage );

			for( Class aClass : names ) {
				
				if ( className != null ) {
					
					if( ! aClass.getName().contains( DOLLAR ) ) {
												
						if( className.equalsIgnoreCase( aClass.getSuperclass().getName() ) ) {
							
							tableDefs.add( aClass );
						}
					}
				}
			}
		} catch ( IOException e ) {
			
			e.printStackTrace();
		}
		
		return tableDefs;
	}
	
	/**
	 * Method used to return all the methods in a class mapped with their names
	 * Only Methods like {@code Object methodName ( Object ) } shall be returned
	 * @param clazz - {@link Class}
	 * @return {@link Map} < {@link String} , {@link Method} > - Map of Name and method pairs
	 * @throws Exception
	 * @author sehgalan
	 * 
	 */

	private static Map < String , Method > getAllConversionMethods ( Class clazz ) throws Exception {
		
		Map< String , Method > methodMap = new HashMap< String , Method >();
		Method [] allMethods = clazz.getMethods();
		
		for ( Method method : allMethods ) {
			
			if ( method.getParameterTypes().length == 1 && method.getReturnType().getSimpleName().equalsIgnoreCase( Object.class.getSimpleName() ) ) {
							
				methodMap.put( method.getName() , method );
			}
		}
		
		return methodMap;
	}
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName - The base package
     * @return {@link Class}[] - The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses( String packageName ) throws ClassNotFoundException, IOException {
        
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
    	assert classLoader != null;
        
    	String path = packageName.replace( DOT , STR_DOUBLE_SLASH );
    	
        Enumeration< URL > resources = classLoader.getResources( path );
        List< File > dirs = new ArrayList< File >();
        while ( resources.hasMoreElements() ) {
            
        	URL resource = resources.nextElement();
            dirs.add( new File( resource.getFile().replaceAll ( STR_PERCENT_20 , STR_SPACE ) ) );
        }
        
        ArrayList< Class > classes = new ArrayList< Class >();
        
        for (File directory : dirs) {
        
        	classes.addAll( findClasses ( directory , packageName ) );
        }

        return classes.toArray( new Class [ classes.size() ] );
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List < Class > findClasses ( File directory , String packageName ) throws ClassNotFoundException {
        
    	List< Class > classes = new ArrayList < Class >();
        if ( ! directory.exists() ) {
        	
            return classes;
        }
        
        File [] files = directory.listFiles();
        for ( File file : files ) {
        	
            if ( file.isDirectory() ) {
            	
                assert  ! file.getName().contains( DOT );
                
                classes.addAll ( findClasses ( file , packageName + DOT + file.getName() ) );
                
            } else if ( file.getName().endsWith( STR_EXT_CLASS ) ) {
            	
                classes.add( Class.forName ( packageName + DOT + file.getName().substring( 0 , file.getName().length() - 6 ) ) );
            }
        }
        
        return classes;
    }
    
    
    /**
	 * This method formats table data and returns it for setting as table row data
	 * @param inputData - {@link Map} < {@link String} , {@link Method} > - Map with method name and method object
	 * @return {@link List}< {@link String}[] > list with each row having methodName and class containing it
     * @throws Exception 
	 */
	public static List< EndPointBase > getMethodsAsList( String className ) throws Exception {
		
		Class clazz = Class.forName( className );
		List < EndPointBase > outputList = new ArrayList < EndPointBase >();

		for( String methodName : getAllConversionMethods ( clazz ).keySet() ) {

			outputList.add( new EndPointBase( methodName , clazz.getSimpleName() ) );		
		}
		return outputList;
	}
	
	/**
	 * Retrieves the variables of the source and target classes
	 * @param sourceTableImplClassName - Class Name for which all the fields are required
	 * @return {@link List}< {@link String}[] > - table rows for $Source and $Target
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public static List<EndPointBase> getDataAsObservableList( String implClassName ) throws SecurityException , ClassNotFoundException { 
		
		List< EndPointBase > outputList = new ArrayList< EndPointBase >();
		Field[] fields = Class.forName( implClassName ).getDeclaredFields();
		
		for ( Field field : fields ) {

				outputList.add( new EndPointBase ( field.getName() , field.getType().getName().contains( DOT ) ? field.getType().getName().substring( field.getType().getName().lastIndexOf( DOT ) + 1 ) : field.getType().getName() ) );
		}
		
		return outputList;
	}
	
	private static FileFilter fileFilter = new FileFilter() {
		
		@Override
		public boolean accept(File pathname) {
			
			if( pathname.getName().endsWith( EXT_MAPPING ) ) {
				
				return true;
			} else {
				
				return false;
			}
			
		}
	};;
	
	public static List<String> getMappings() {
		
		List<String> mappings = new ArrayList<String>();
		
		File mappingFolder = new File( "D:\\mappings" );
		if ( mappingFolder.exists() && mappingFolder.isDirectory() ) {
			
			File[] files = mappingFolder.listFiles( fileFilter );
			for ( File file : files ) {
				
				mappings.add( file.getName().replace( EXT_MAPPING , STR_EMPTY ) );
			}
		}
		
		return mappings;
	}
	
	public static void removeMappingBackups() {
			
		File file = new File( "D:\\mappings" );
		FileFilter fileFilter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				
				if ( pathname.getName().endsWith( EXT_MAPPING + UNDERSCORE + BAK ) ) {
					
					return true;
				} else {
					
					return false;
				}
			}
		};
		File[] backups = null;
		if ( file.exists() && file.isDirectory() ) {
			
			backups = file.listFiles( fileFilter );
			
		}
		if (backups == null) {
			return;
		}
		for ( File backup : backups ) {
			
			backup.delete();
		}
	}
}
