package com.successfactors.sca.service;

import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommand;
import com.successfactors.sca.ServiceCommandImpl;

/**
 * Created by Xc on 2016/4/19.
 */
public class SimpleServiceCommandHandler implements ServiceCommandHandler {

    public <R, C extends ServiceCommand<R>> R execute(C cmd) throws ServiceApplicationException {
        String className = cmd.getClass().getName();
        try {
            Class implClass = Class.forName(getImplClassName(className));
            ServiceCommandImpl<R, C> impl = (ServiceCommandImpl<R, C>) implClass.newInstance();
            return impl.execute(cmd);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ServiceApplicationException();
        }
    }

    private String getImplClassName(String className){
        String[] strs = className.split("\\.");
        String s = "";
        //org.xxx.Cmd
        //org.xxx.impl.CmdImpl
        for(int i = 0 ; i < strs.length-1 ; i ++){
            s+=strs[i]+ "";
        }
        s += "impl.";
        s += strs[strs.length-1];
        s += "Impl";
        return s;
    }
}
