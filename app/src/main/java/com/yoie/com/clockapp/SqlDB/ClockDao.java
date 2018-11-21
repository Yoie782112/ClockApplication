package com.yoie.com.clockapp.SqlDB;

import com.yoie.com.clockapp.Model.Clock;

import java.util.List;

public interface ClockDao {
    public Clock add(Clock item);
    public boolean delete(long id);
    public void deleteAll();
    public boolean update(Clock item);
    public Clock findById(long id);
    public List<Clock> getAll();
    public void test();

}
