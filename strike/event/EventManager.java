package strike.event;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class EventManager {
    private static final Map<Class<? extends Event>, ArrayList<EventData>> REGISTRY_MAP;
    
    static {
        REGISTRY_MAP = (Map)new HashMap();
    }
    
    private static void sortListValue(final Class<? extends Event> clazz) {
        final ArrayList<EventData> flexableArray = (ArrayList<EventData>)new ArrayList();
        byte[] value_ARRAY;
        for (int length = (value_ARRAY = EventPriority.VALUE_ARRAY).length, i = 0; i < length; ++i) {
            final byte b = value_ARRAY[i];
            for (final EventData methodData : (ArrayList)EventManager.REGISTRY_MAP.get(clazz)) {
                if (methodData.priority == b) {
                    flexableArray.add(methodData);
                }
            }
        }
        EventManager.REGISTRY_MAP.put(clazz, flexableArray);
    }
    
    private static boolean isMethodBad(final Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent((Class)EventTarget.class);
    }
    
    private static boolean isMethodBad(final Method method, final Class<? extends Event> clazz) {
        return isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
    }
    
    public static ArrayList<EventData> get(final Class<? extends Event> clazz) {
        return (ArrayList<EventData>)EventManager.REGISTRY_MAP.get(clazz);
    }
    
    public static void cleanMap(final boolean removeOnlyEmptyValues) {
        final Iterator<Map.Entry<Class<? extends Event>, ArrayList<EventData>>> iterator = (Iterator<Map.Entry<Class<? extends Event>, ArrayList<EventData>>>)EventManager.REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!removeOnlyEmptyValues || ((ArrayList)((Map.Entry)iterator.next()).getValue()).isEmpty()) {
                iterator.remove();
            }
        }
    }
    
    public static void unregister(final Object o, final Class<? extends Event> clazz) {
        if (EventManager.REGISTRY_MAP.containsKey(clazz)) {
            for (final EventData methodData : (ArrayList)EventManager.REGISTRY_MAP.get(clazz)) {
                if (methodData.source.equals(o)) {
                    ((ArrayList)EventManager.REGISTRY_MAP.get(clazz)).remove(methodData);
                }
            }
        }
        cleanMap(true);
    }
    
    public static void unregister(final Object o) {
        for (final ArrayList<EventData> flexableArray : EventManager.REGISTRY_MAP.values()) {
            for (int i = flexableArray.size() - 1; i >= 0; --i) {
                if (((EventData)flexableArray.get(i)).source.equals(o)) {
                    flexableArray.remove(i);
                }
            }
        }
        cleanMap(true);
    }
    
    public static void register(final Method method, final Object o) {
        final Class<?> clazz = method.getParameterTypes()[0];
        final EventData methodData = new EventData(o, method, ((EventTarget)method.getAnnotation((Class)EventTarget.class)).value());
        if (!methodData.target.isAccessible()) {
            methodData.target.setAccessible(true);
        }
        if (EventManager.REGISTRY_MAP.containsKey(clazz)) {
            if (!((ArrayList)EventManager.REGISTRY_MAP.get(clazz)).contains(methodData)) {
                ((ArrayList)EventManager.REGISTRY_MAP.get(clazz)).add(methodData);
                sortListValue(clazz);
            }
        }
        else {
            EventManager.REGISTRY_MAP.put(clazz, new ArrayList<EventData>(methodData) {
                {
                    this.add((Object)eventData);
                }
            });
        }
    }
    
    public static void register(final Object o, final Class<? extends Event> clazz) {
        Method[] methods;
        for (int length = (methods = o.getClass().getMethods()).length, i = 0; i < length; ++i) {
            final Method method = methods[i];
            if (!isMethodBad(method, clazz)) {
                register(method, o);
            }
        }
    }
    
    public static void register(final Object o) {
        Method[] methods;
        for (int length = (methods = o.getClass().getMethods()).length, i = 0; i < length; ++i) {
            final Method method = methods[i];
            if (!isMethodBad(method)) {
                register(method, o);
            }
        }
    }
}
