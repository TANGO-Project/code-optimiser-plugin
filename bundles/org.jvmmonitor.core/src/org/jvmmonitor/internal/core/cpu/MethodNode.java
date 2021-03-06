/*******************************************************************************
 * Copyright (c) 2010 JVM Monitor project. All rights reserved. 
 * 
 * This code is distributed under the terms of the Eclipse Public License v1.0
 * which is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.jvmmonitor.internal.core.cpu;

import java.lang.management.ThreadMXBean;
import java.util.List;

import org.jvmmonitor.core.cpu.ICpuModel;
import org.jvmmonitor.core.cpu.ITreeNode;

/**
 * The method node on Hot Spots.
 */
public class MethodNode extends AbstractMethodNode {

    /**
     * The constructor.
     * 
     * @param cpuModel
     *            The cpuModel
     * @param name
     *            The qualified method name
     * @param thread
     *            The thread node
     */
    public MethodNode(ICpuModel cpuModel, String name,
            ThreadNode<MethodNode> thread, double energy) {
        super(cpuModel, name, thread, energy);

        selfTime = 0;
        totalEnergy = 0;
        invocationCount = 0;
    }

    /*
     * @see ICallTreeNode#getChildren()
     */
    @Override
    public List<MethodNode> getChildren() {
        return null;
    }

    /*
     * @see ITreeNode#getChild(String)
     */
    @Override
    public MethodNode getChild(String name) {
        return null;
    }

    /*
     * @see ICallTreeNode#hasChildren()
     */
    @Override
    public boolean hasChildren() {
        return false;
    }

    /*
     * @see ITreeNode#getParent()
     */
    @Override
    public ITreeNode getParent() {
        return threadNode;
    }

    /*
     * @see AbstractMethodNode#clear()
     */
    @Override
    public void clear() {
        selfTime = 0;
        invocationCount = 0;
    }

    /*
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MethodNode)) {
            return false;
        }
        MethodNode methodNode = (MethodNode) obj;

        if (methodNode.getName().equals(qualifiedMethodName)
                && methodNode.getSelfTime() == selfTime
                && methodNode.getInvocationCount() == invocationCount) {
            return true;
        }

        return false;
    }

    /*
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getName()).append('\t');
        buffer.append(getSelfTime()).append('\t');
        buffer.append(getSelfTimeInPercentage()).append('\t');
        buffer.append(getInvocationCount());
        return buffer.toString();
    }

    /**
     * Increments the sum of method invocation time.
     * 
     * @param time
     *            The method self invocation time
     */
    public void incrementTime(long time) {
        selfTime += time;
    }
    
    /**
     * Updates the method nodes energy and power values.
     * @param power The power in Watts
     * @param duration The time in milliseconds
     */
    public void incrementAveragePower(double power, long duration) {
        if (power > 0 && Double.isFinite(power)) {
            totalEnergy = totalEnergy + (power * (duration/ 1000d));
        }
	}
    /**
     * This increments the energy consumption of this method node
     * @param energy
     */
    public void incrementTotalEnergy(double energy) {
        if (energy > 0 && Double.isFinite(energy)) {
            totalEnergy = totalEnergy + energy; 
        }
    }   
    
    /**
     * 
     * @param usage
     * @param duration
     */
    public void incrementAverageCpuUsage(double usage, long duration) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Increments the sum of method invocation count.
     * 
     * @param count
     *            The method invocation count
     */
    public void incrementCount(int count) {
        invocationCount += count;
    }
    
}
