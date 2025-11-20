-- Add status column to promotions table
ALTER TABLE promotions ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL;
