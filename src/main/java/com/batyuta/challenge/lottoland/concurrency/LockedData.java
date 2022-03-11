/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.batyuta.challenge.lottoland.concurrency;

import javax.persistence.Transient;
import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * Abstract class for Locking the Write/Read properties.
 */
public abstract class LockedData {
    /**
     * Max retries count.
     */
    public static final int MAX_TRYING = 5;
    /**
     * Waiting time account.
     */
    public static final int WAITING_TIME = 1;
    /**
     * Waiting time unit.
     */
    public static final TimeUnit WAITING_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * General lock object.
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Write Lock object.
     */
    private final Lock writeLock = lock.writeLock();

    /**
     * Read Lock object.
     */
    private final Lock readLock = lock.readLock();

    /**
     * Locked read operation.
     *
     * @param command command
     * @param <T>     returned type
     * @return object
     */
    @Transient
    public <T> T read(Supplier<T> command) {
        return lockedOperation(readLock, command);
    }

    /**
     * Locked write operation.
     *
     * @param command command
     * @param <T>     returned type
     * @return object
     */
    @Transient
    public <T> T write(Supplier<T> command) {
        return lockedOperation(writeLock, command);
    }

    /**
     * The base locked operation.
     *
     * @param readOrWriteLock lock type
     * @param command         command
     * @param <T>             returned type
     * @return object
     */
    private <T> T lockedOperation(Lock readOrWriteLock, Supplier<T> command) {
        try {
            boolean isLockAcquired;
            int trying = 0;
            do {
                isLockAcquired = readOrWriteLock
                        .tryLock(WAITING_TIME, WAITING_TIME_UNIT);
                trying++;
            } while (!isLockAcquired && trying < MAX_TRYING);
            if (isLockAcquired) {
                try {
                    return command.get();
                } finally {
                    readOrWriteLock.unlock();
                }
            } else {
                throw new ConcurrentModificationException();
            }
        } catch (InterruptedException e) {
            throw new ConcurrentModificationException(e.getMessage(), e);
        }
    }
}
