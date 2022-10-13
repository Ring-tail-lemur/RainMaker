package org.example.functions.util.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
//야호
public class LowerCaseSnakePhysicalNamingStrategy implements PhysicalNamingStrategy {
	@Override
	public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		if (identifier == null) {
			return null;
		}
		return convertToSnakeUpperCase(identifier);
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		if (identifier == null) {
			return null;
		}
		return convertToSnakeUpperCase(identifier);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return convertToSnakeUpperCase(identifier);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return convertToSnakeUpperCase(identifier);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return convertToSnakeUpperCase(identifier);
	}

	private Identifier convertToSnakeUpperCase(final Identifier identifier) {
		final String regex = "([a-z])([A-Z])";
		final String replacement = "$1_$2";
		final String newName = identifier.getText()
			.replaceAll(regex, replacement)
			.toLowerCase();
		return Identifier.toIdentifier(newName);
	}
}
